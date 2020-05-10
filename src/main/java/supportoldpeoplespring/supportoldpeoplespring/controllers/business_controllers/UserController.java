package supportoldpeoplespring.supportoldpeoplespring.controllers.business_controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import supportoldpeoplespring.supportoldpeoplespring.documents.Role;
import supportoldpeoplespring.supportoldpeoplespring.documents.User;
import supportoldpeoplespring.supportoldpeoplespring.dtos.UserDto;
import supportoldpeoplespring.supportoldpeoplespring.dtos.UserMinimumDto;
import supportoldpeoplespring.supportoldpeoplespring.dtos.UserProfileDto;
import supportoldpeoplespring.supportoldpeoplespring.dtos.output.TokenOutputDto;
import supportoldpeoplespring.supportoldpeoplespring.exceptions.BadRequestException;
import supportoldpeoplespring.supportoldpeoplespring.exceptions.ForbiddenException;
import supportoldpeoplespring.supportoldpeoplespring.exceptions.NotFoundException;
import supportoldpeoplespring.supportoldpeoplespring.repositories.UserRepository;
import supportoldpeoplespring.supportoldpeoplespring.services.JwtService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public TokenOutputDto login(String mobile) {
        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Unexpected!!. Mobile not found:" + mobile));
        String[] roles = Arrays.stream(user.getRoles()).map(Role::name).toArray(String[]::new);
        return new TokenOutputDto(jwtService.createToken(user.getMobile(), user.getUsername(), roles));
    }

    public UserDto readUser(String mobile, String claimMobile, List<String> claimRoles) {
        User user = this.userRepository.findByMobile(mobile)
                .orElseThrow(() -> new NotFoundException("User mobile:" + mobile));
        this.authorized(claimMobile, claimRoles, mobile, Arrays.stream(user.getRoles())
                .map(Role::roleName).collect(Collectors.toList()));
        return new UserDto(user);
    }

    private void authorized(String claimMobile, List<String> claimRoles, String userMobile, List<String> userRoles) {
        if (claimRoles.contains(Role.ADMIN.roleName()) || claimMobile.equals(userMobile)) {
            return;
        }
        if (claimRoles.contains(Role.VOLUNTARY.roleName())
                && !userRoles.contains(Role.ADMIN.roleName()) && !userRoles.contains(Role.ADMIN.roleName())) {
            return;
        }
        if (claimRoles.contains(Role.CLIENT.roleName())
                && userRoles.stream().allMatch(role -> role.equals(Role.AUTHENTICATED.roleName()))) {
            return;
        }
        throw new ForbiddenException("User mobile (" + userMobile + ")");
    }

    public List<UserMinimumDto> readAll() {
        return this.userRepository.findAllUsers();
    }
    public Boolean validatorPassword(String mobile, UserProfileDto userProfileDto) {
        if (mobile == null || !mobile.equals(userProfileDto.getMobile()))
            throw new BadRequestException("User mobile (" + userProfileDto.getMobile() + ")");

        User user = this.userRepository.findByMobile(mobile).orElseThrow(() -> new NotFoundException("User mobile (" + mobile + ")"));;
        return new BCryptPasswordEncoder().matches(userProfileDto.getPassword(),user.getPassword());
    }

    public List<UserMinimumDto> readAllByUsernameDniAddressRoles(String mobile,String username,String dni, String address,  Role[] roles) {
        return this.userRepository.findByMobileUsernameDniAddressLikeNullSafeandRoles(mobile,username,dni,address,roles);
    }
}

