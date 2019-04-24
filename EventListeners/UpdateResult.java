import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

// Import Testlin API related jars
import testlink.api.java.client.TestLinkAPIClient
import testlink.api.java.client.TestLinkAPIException
import testlink.api.java.client.TestLinkAPIResults

import java.util.concurrent.atomic.AtomicInteger

class UpdateResults 
{
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	AtomicInteger passedTC 		= new AtomicInteger(0);
	AtomicInteger failedTC 		= new AtomicInteger(0);
		
	public static String DEVKEY="place_your_testLinks_Personal_API_access_key_here";

	public static String URL="http://yourdomain.com/tm/lib/api/xmlrpc/v1/xmlrpc.php";
	
	
	public static void reportResult(String TestProject,String TestPlan,String Testcase,String Build,String Notes,String Result) throws TestLinkAPIException
	{
		TestLinkAPIClient api = new TestLinkAPIClient(DEVKEY, URL);
		api.reportTestCaseResult(TestProject, TestPlan, Testcase, Build, Notes, Result);
	}

	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) 
	{
		/*
			..You can put code to do stuff BEFORE test case execution here e.g :
			
		
					println testCaseContext.getTestCaseId();
					println testCaseContext.getTestCaseVariables();
					println "testCaseContext.getTextCaseId()=${testCaseContext.getTestCaseId()}";
		*/
		
	}

	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) 
	{				
			
		UpdateResults	testlinkUpdate 	= new UpdateResults();
		
		String testProject				="YourTestProjectName";
		String testPlan					="YourTestPlanName";
		/* Get the TestCase Name from Katalon . This means that a testcase 
		 * should have same name in both Katalon and TestLink */
		String testCase					= testCaseContext.getTestCaseId().substring(11); // remove the "Test Cases/" part of testCaseContext.getTestCaseId()
		String build					="SampleBuildName";
		String notes					=null;
		String result					=null;
			
		if (testCaseContext.getTestCaseStatus() == 'PASSED')
		{
				
			result = TestLinkAPIResults.TEST_PASSED;
			notes="Executed and updated successfully from Katalon test automation framework ";
			testlinkUpdate.reportResult(testProject, testPlan, testCase, build, notes, result);
			passedTC.getAndIncrement();
		}
		else if (testCaseContext.getTestCaseStatus() == 'FAILED')
		{
				
			result=TestLinkAPIResults.TEST_FAILED;
			notes="Execution failed.This test has been executed on Katalon automation framework.";
			testlinkUpdate.reportResult(testProject, testPlan, testCase, build, notes, result);
			failedTC.getAndIncrement();
		}			
	
			println testCaseContext.getTestCaseId();
			println testCaseContext.getTestCaseStatus();
		
	}
	
	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) 
	{ 
		println testSuiteContext.getTestSuiteId();

		
		
	}
	
	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext)
	{
		println "Number of passed TCs is: " + passedTC.get();
		println "Number of failed TCs is: " + failedTC.get();
		
		
	}
	
	
	
	
}
