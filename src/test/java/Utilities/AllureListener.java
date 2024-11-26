package Utilities;

import ProjectBase.TestBaseClass;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.IExecutionListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AllureListener extends TestBaseClass implements ITestListener, IExecutionListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return new byte[0];
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = getDriver();
        if (driver != null) {
            saveScreenshotPNG(getDriver());
        }
        Allure.step("Test failed: " + getTestMethodName(result));
        if (result.getThrowable() != null) {
            Allure.step("Error message: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onStart(ITestContext context) {
        clearAllureResultsAndReports();
    }

    @Override
    public void onFinish(ITestContext context) {
        generateAndOpenAllureReport();
    }

    public void clearAllureResultsAndReports() {
        String projectDir = System.getProperty("user.dir") + File.separator + "reports";
        String allureResultsDir = projectDir + File.separator + "allure-results";
        String allureReportDir = projectDir + File.separator + "allure-report";

        deleteDirectory(new File(allureResultsDir));
        deleteDirectory(new File(allureReportDir));

        // Ensure allure-results directory exists
        new File(allureResultsDir).mkdirs();
    }

    public void deleteDirectory(File directoryToBeDeleted) {
        if (directoryToBeDeleted.exists()) {
            File[] allContents = directoryToBeDeleted.listFiles();
            if (allContents != null) {
                for (File file : allContents) {
                    deleteDirectory(file);
                }
            }
            if (!directoryToBeDeleted.delete()) {
                System.err.println("Failed to delete: " + directoryToBeDeleted.getAbsolutePath());
            }
        }
    }

    public void generateAndOpenAllureReport() {
        String projectDir = System.getProperty("user.dir") + File.separator + "reports";
        String allureResultsDir = projectDir + File.separator + "allure-results";
        String allureReportDir = projectDir + File.separator + "allure-report";

        try {
            // Generate Allure Report using Allure CLI
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "cmd.exe", "/c", "allure generate " + allureResultsDir + " -o " + allureReportDir);
            processBuilder.directory(new File(projectDir)); // Set working directory
            processBuilder.inheritIO(); // Display output in the console
            Process process = processBuilder.start();
            int exitCode = process.waitFor(); // Wait for the process to finish

            if (exitCode == 0) {
                System.out.println("Allure report generated successfully.");
            } else {
                System.err.println("Failed to generate Allure report. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error while generating Allure report: " + e.getMessage());
        }
    }
}



