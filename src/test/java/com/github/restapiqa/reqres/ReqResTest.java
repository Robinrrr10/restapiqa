package com.github.restapiqa.reqres;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.restapiqa.clients.Client;
import com.github.restapiqa.utils.FileUtils;
import com.github.restapiqa.validators.Compare;

import io.restassured.response.Response;

public class ReqResTest {

	Client client = new Client();

	@Test
	public void giveAllSameEndpointsInBothFilesAndValidate() {
		String file1Path = "files/file1.txt";
		String file2Path = "files/file2.txt";
		Assert.assertTrue(this.callAllEndpointsAndValidate(file1Path, file2Path));
	}
	
	@Test
	public void giveAllDifferentEndpointsAndValidate() {
		String file1Path = "files/file1.txt";
		String file2Path = "files/file3.txt";
		Assert.assertFalse(this.callAllEndpointsAndValidate(file1Path, file2Path));
	}
	
	@Test
	public void giveFewDifferentEndpointsAndValidate() {
		String file1Path = "files/file1.txt";
		String file2Path = "files/file4.txt";
		Assert.assertFalse(this.callAllEndpointsAndValidate(file1Path, file2Path));
	}
	
	@Test
	public void giveInvalidTextAsEndpointsAndValidate() {
		String file1Path = "files/file1.txt";
		String file2Path = "files/file5.txt";
		Assert.assertFalse(this.callAllEndpointsAndValidate(file1Path, file2Path));
	}
	
	@Test
	public void giveAnEmptyAsEndpointAndValidate() {
		String file1Path = "files/file1.txt";
		String file2Path = "files/file6.txt";
		Assert.assertFalse(this.callAllEndpointsAndValidate(file1Path, file2Path));
	}
	
	@Test
	public void giveMoreLinesInAnFileAndValidate() {
		String file1Path = "files/file1.txt";
		String file2Path = "files/file7.txt";
		Assert.assertFalse(this.callAllEndpointsAndValidate(file1Path, file2Path));
	}
	
	
	private boolean callAllEndpointsAndValidate(String file1Path, String file2Path) {
		
		boolean isBothValid = true;
		
		List<String> file1AllEndpoints = FileUtils.getAllLines(file1Path);
		List<String> file2AllEndpoints = FileUtils.getAllLines(file2Path);

		int maxLen = file1AllEndpoints.size() > file2AllEndpoints.size() ? file1AllEndpoints.size()
				: file2AllEndpoints.size();

		for (int i = 0; i < maxLen; i++) {
			if ((i <= file1AllEndpoints.size() - 1) && (i <= file2AllEndpoints.size() - 1)) {
				
				String url1 = file1AllEndpoints.get(i);
				String url2 = file2AllEndpoints.get(i);
				
				if(!url1.isEmpty() && !url2.isEmpty()) {
				Response response1 = client.callApi(url1);
				Response response2 = client.callApi(url2);
				
				if(response1 != null && response2 != null) {
					boolean isEqual = Compare.compareResponse(response1.body().asString(), response2.body().asString());
					if(!isEqual)
						isBothValid = false;
					Compare.printResult(url1, url2, isEqual);
				}else if(response1 == null && response2 != null) {
					System.out.println("Response of endpoint " + url1 + " is null.");
					isBothValid = false;
				}else if(response1 != null && response2 == null) {
					System.out.println("Response of endpoint " + url2 + " is null.");
					isBothValid = false;
				}
				
				}else if(url1.isEmpty() && !url2.isEmpty()) {
					System.out.println("Line no: " + (i + 1) + " empty in " + file1Path);
					isBothValid = false;
				} else if(!url1.isEmpty() && url2.isEmpty()){
					System.out.println("Line no: " + (i + 1) + " empty in " + file2Path);
					isBothValid = false;
				}
			
			} else if ((i > file1AllEndpoints.size() - 1) && (i <= file2AllEndpoints.size() - 1)) {
				System.out.println("In Line no " + (i + 1) + " " + file1Path + " has no content. But " + file2Path + " has content");
				isBothValid = false;
			} else if ((i <= file1AllEndpoints.size() - 1) && (i > file2AllEndpoints.size() - 1)) {
				System.out.println("In Line no " + (i + 1) + " " + file1Path + " has content. But " + file2Path + " has no content");
				isBothValid = false;
			}
		}
		return isBothValid;
	}

}
