package testcases;

import base.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AdminCoursesPage.CourseVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IdentifyCoursesTest extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(IdentifyCoursesTest.class);

    @BeforeMethod
    public void setUp() {
        logger.info("Setting up test...");
        super.setUp();
        if (this.adminCoursesPage == null) {
            logger.error("adminCoursesPage is null after super.setUp(). Check BasePage initialization.");
            throw new RuntimeException("adminCoursesPage is not initialized in BasePage.");
        }
    }

    @Test
    public void testScrapeCourseRatings() {
        logger.info("Starting test to scrape course ratings for multiple courses...");
        String adminUrl = "https://www.coursera.org/admin-v2/ibm-skills-network/home/courses";

        // Updated list of courses to include all provided course names
        List<String> courseNames = Arrays.asList(
            "Hands-on Introduction to Linux Commands and Shell Scripting",
            "IBM Containers: Docker, Kubernetes, OpenShift",
            "Introduction to NoSQL Databases",
            "Introduction to Software, Programming, and Databases",
            "Developing Applications with SQL, Databases, and Django",
            "Introduction to Cloud Computing",
            "Introduction to Web Development with HTML, CSS, JavaScript",
            "Getting Started with Git and GitHub",
            "Introduction to Containers w/ Docker, Kubernetes & OpenShift",
            "Django Application Development with SQL and Databases",
            "Application Development using Microservices and Serverless",
            "Full Stack Application Development Capstone Project",
            "Full Stack Software Developer Assessment",
            "Developing Front-End Apps with React",
            "Developing Back-End Apps with Node.js and Express",
            "Back-end Application Development Capstone Project",
            "Introduction to DevOps",
            "Introduction to Agile Development and Scrum",
            "Introduction to Test and Behavior Driven Development",
            "Continuous Integration and Continuous Delivery (CI/CD)",
            "Application Security for Developers and DevOps Professionals",
            "DevOps Capstone Project",
            "Monitoring and Observability for Development and DevOps",
            "Java Programming for Beginners",
            "Object Oriented Programming in Java",
            "Cloud Native, Microservices, Containers, DevOps and Agile",
            "Spring Framework Fundamentals",
            "Intermediate Web and Front-End Development",
            "Designing User Interfaces and Experiences (UI/UX)",
            "Front-End Development Capstone Project",
            "Developing Websites and Front-Ends with Bootstrap",
            "Getting Started with Front-End and Web Development",
            "Introduction to HTML, CSS, & JavaScript",
            "JavaScript Programming Essentials",
            "Node.js & MongoDB: Developing Back-end Database Applications",
            "JavaScript Full Stack Capstone Project",
            "JavaScript Back-End Capstone Project",
            "Introduction to Software Engineering",
            "Get Started with Cloud Native, DevOps, Agile, and NoSQL",
            "Get Started with Android App Development",
            "Get Started with iOS App Development",
            "Introduction to Mobile App Development",
            "React Native: Developing Android and iOS Apps",
            "Flutter and Dart: Developing iOS, Android, and Mobile Apps",
            "Mobile App Notifications, Databases, & Publishing",
            "Mobile App Development Capstone Project",
            "Generative AI: Turbocharge Mobile App Development",
            "Introduction to Artificial Intelligence (AI)",
            "Building AI Powered Chatbots Without Programming",
            "Developing AI Applications with Python and Flask",
            "Generative AI: Introduction and Applications",
            "Generative AI: Prompt Engineering Basics",
            "Building Generative AI-Powered Applications with Python",
            "Introduction to Technical Support",
            "Introduction to Hardware and Operating Systems",
            "Introduction to Networking and Storage",
            "Technical Support (IT) Case Studies and Capstone"
        );

        List<CourseVersion> allCourseVersions = new ArrayList<>();

        for (String courseName : courseNames) {
            logger.info("Processing course: {}", courseName);
            adminCoursesPage.navigateToAdminCourses(adminUrl);
            List<CourseVersion> courseVersions = adminCoursesPage.searchAndGetCourseVersions(courseName);

            if (courseVersions.isEmpty()) {
                logger.warn("No versions found for course: {}. Check the course name, logs, and screenshots for details.", courseName);
                continue;
            }

            allCourseVersions.addAll(courseVersions);

            logger.info("Scraped ratings data for course: {}", courseName);
            for (CourseVersion courseVersion : courseVersions) {
                logger.info("Course Name: {}, Version: {}, Link: {}, Rating: {}", 
                    courseVersion.getCourseName(), courseVersion.getVersion(), courseVersion.getLink(), courseVersion.getRating());
            }
        }

        if (allCourseVersions.isEmpty()) {
            logger.warn("No course versions were found for any of the specified courses.");
            return;
        }

        adminCoursesPage.writeToExcel("coursera_course_ratings_latest.xlsx", allCourseVersions);
        logger.info("Test completed successfully.");
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test...");
        closeDriver();
        logger.info("Test cleanup completed.");
    }
}