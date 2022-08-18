package tests.UI;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.example.dto.Project;
import org.example.pages.LoginPage;
import org.example.pages.NewProjectModal;
import org.example.pages.NewProjectPage;
import org.example.pages.ProjectSettingsPage;
import org.example.steps.ProjectSteps;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.$x;


public class CreateProjectTest {

    String projectName;
    String codeName;

    @BeforeMethod
    public void login() {
        new LoginPage().loginStandardUser();
    }

  @Test
  public void createValidProject() {

      Faker faker = new Faker();
      projectName = faker.code().asin();
      codeName = faker.code().asin();

      Project project = Project.builder()
                               .title(projectName)
                               .code(codeName)
                               .description(faker.company().catchPhrase())
                               .build();

      new NewProjectPage().open();
      new NewProjectModal().fillInNewProjectModal(project)
                           .clickOnPublicButton()
                           .clickOnCreateProjectButton();
      $x("//span[text()='Create new suite']").shouldBe(Condition.visible);

      Project actualProject = new ProjectSettingsPage().open(projectName).getProjectSettings();
      Assert.assertEquals(actualProject, project);
  }

    @AfterMethod
    public void deleteProjectAndSignOut() {
        new ProjectSteps().deleteProject(codeName)
                          .signOut();
    }
}