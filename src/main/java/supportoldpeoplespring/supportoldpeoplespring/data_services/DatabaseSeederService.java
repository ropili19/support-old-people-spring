package supportoldpeoplespring.supportoldpeoplespring.data_services;



import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import supportoldpeoplespring.supportoldpeoplespring.documents.Role;
import supportoldpeoplespring.supportoldpeoplespring.documents.User;
import supportoldpeoplespring.supportoldpeoplespring.repositories.UserRepository;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class DatabaseSeederService {

    private static final String VARIOUS_CODE = "1";

    private static final String VARIOUS_NAME = "Varios";

    private static final String PREFIX_CODE_ARTICLE = "84";

    private static final Long FIRST_CODE_ARTICLE = 840000000000L;

    private static final Long LAST_CODE_ARTICLE = 840000099999L;


    @Autowired
    private Environment environment;

    @Value("${miw.admin.mobile}")
    private String mobile;
    @Value("${miw.admin.username}")
    private String username;
    @Value("${miw.admin.password}")
    private String password;

    @Value("${miw.databaseSeeder.ymlFileName:#{null}}")
    private String ymlFileName;

    @Autowired
    private UserRepository userRepository;


    @PostConstruct
    public void constructor() {
        String[] profiles = this.environment.getActiveProfiles();
        if (Arrays.stream(profiles).anyMatch("dev"::equals)) {
            this.deleteAllAndInitializeAndLoadYml();
        } else if (Arrays.stream(profiles).anyMatch("prod"::equals)) {
            this.initialize();
        }
    }

    private void initialize() {
        if (!this.userRepository.findByMobile(this.mobile).isPresent()) {
            LogManager.getLogger(this.getClass()).warn("------- Create Admin -----------");
            User user = new User(this.mobile, this.username, this.password);
            user.setRoles(new Role[]{Role.ADMIN});
            this.userRepository.save(user);
        }

    }

    public void deleteAllAndInitialize() {
        LogManager.getLogger(this.getClass()).warn("------- Delete All -----------");
        // Delete Repositories -----------------------------------------------------
          this.userRepository.deleteAll();
        // -------------------------------------------------------------------------
        this.initialize();
    }

    public void deleteAllAndInitializeAndLoadYml() {
        this.deleteAllAndInitialize();
        this.seedDatabase();
        this.initialize();
    }

    public void seedDatabase() {
        if (this.ymlFileName != null) {
            try {
                LogManager.getLogger(this.getClass()).warn("------- Initial Load: " + this.ymlFileName + "-----------");
                this.seedDatabase(new ClassPathResource(this.ymlFileName).getInputStream());
            } catch (IOException e) {
                LogManager.getLogger(this.getClass()).error("File " + this.ymlFileName + " doesn't exist or can't be opened");
            }
        } else {
            LogManager.getLogger(this.getClass()).error("File db.yml doesn't configured");
        }

      //  this.seedDatabaseWithArticlesFamilyForView();
    }

    public void seedDatabase(InputStream input) {
        Yaml yamlParser = new Yaml(new Constructor(DatabaseGraph.class));
        DatabaseGraph tpvGraph = yamlParser.load(input);


        this.userRepository.saveAll(tpvGraph.getUserList());


        LogManager.getLogger(this.getClass()).warn("------- Seed...   " + "-----------");
    }
/*
    public String nextCodeEan() {
        Article article = this.articleRepository.findFirstByCodeStartingWithOrderByRegistrationDateDescCodeDesc(PREFIX_CODE_ARTICLE);

        Long nextCodeWithoutRedundancy = FIRST_CODE_ARTICLE;

        if (article != null) {
            String code = article.getCode();
            String codeWithoutRedundancy = code.substring(0, code.length() - 1);

            nextCodeWithoutRedundancy = Long.parseLong(codeWithoutRedundancy) + 1L;
        }

        if (nextCodeWithoutRedundancy > LAST_CODE_ARTICLE) {
            throw new ConflictException("There is not next code EAN");
        }

        return new Barcode().generateEan13code(nextCodeWithoutRedundancy);
    }

    private void seedDatabaseWithArticlesFamilyForView() {
        LogManager.getLogger(this.getClass()).warn("------- Create Article Family Root -----------");
        ArticlesFamily root = new FamilyComposite(FamilyType.ARTICLES, "root", "root");

        ArticlesFamily c1 = new FamilyArticle(this.articleRepository.findById("8400000000031").get());
        ArticlesFamily c2 = new FamilyArticle(this.articleRepository.findById("8400000000048").get());
        this.articlesFamilyRepository.save(c1);
        this.articlesFamilyRepository.save(c2);
        ArticlesFamily c3 = new FamilyComposite(FamilyType.ARTICLES, "c", "cards");
        ArticlesFamily c4 = new FamilyComposite(FamilyType.SIZES, null, "X");
        ArticlesFamily c5 = new FamilyComposite(FamilyType.SIZES, "Zz Falda", "Zarzuela - Falda");

        ArticlesFamily c6 = new FamilyArticle(this.articleRepository.findById("8400000000017").get());
        ArticlesFamily c7 = new FamilyArticle(this.articleRepository.findById("8400000000024").get());


        this.articlesFamilyRepository.save(c3);
        this.articlesFamilyRepository.save(c4);
        this.articlesFamilyRepository.save(c5);
        this.articlesFamilyRepository.save(c6);
        this.articlesFamilyRepository.save(c7);

        root.add(c1);
        root.add(c2);
        root.add(c3);
        root.add(c4);
        root.add(c5);

        c5.add(c6);
        c5.add(c7);

        this.articlesFamilyRepository.save(root);
        this.articlesFamilyRepository.save(c5);

    }
*/
}





