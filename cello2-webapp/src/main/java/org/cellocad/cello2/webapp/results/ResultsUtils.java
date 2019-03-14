/**
 * Copyright (C) 2019 Boston University (BU)
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cellocad.cello2.webapp.results;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.CelloWebException;
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.project.ProjectUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-02-23
 *
 */
public class ResultsUtils {

	/**
	 * @return
	 */
	private static String newResultId() {
		String rtn = null;
		rtn = (new ObjectId()).toString();
		return rtn;
	}

	public static JsonNode getResultsFilter(String application) throws CelloWebException {
		JsonNode rtn = null;
		String filename = application + ".json";
		String filepath = "";
		filepath += "results/filter/";
		filepath += filename;
		ObjectMapper mapper = new ObjectMapper();
		try {
			String str = Utils.getResourceAsString(filepath);
			rtn = mapper.readTree(str);
		} catch (IOException e) {
			throw new CelloWebException("Error with file " + filename + ".");
		}
		return rtn;
	}

	public static String getResultsFile(String userId, String name) {
		String rtn = "";
		rtn += ProjectUtils.getProjectDirectory(userId,name);
		rtn += Utils.getFileSeparator();
		rtn += "results.json";
		return rtn;
	}

	public static void createResultsFile(String userId, String name) throws CelloWebException {
		String application = ProjectUtils.getProjectApplication(userId,name);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode filter = ResultsUtils.getResultsFilter(application);
		ArrayNode results = mapper.createArrayNode();
		String filepath = ResultsUtils.getResultsFile(userId,name);
		Utils.createFile(filepath);
		File root = new File(ProjectUtils.getProjectDirectory(userId,name));
		File[] list = root.listFiles();
		if (list != null) {
			for (File file : list) {
				for (JsonNode node : filter) {
					String pattern = node.get("pattern").asText();
					Pattern p = Pattern.compile(pattern.replaceAll("\\\\+", "\\\\"));
					Matcher m = p.matcher(file.getName());
					if (m.matches()) {
						ObjectNode result = mapper.createObjectNode();
						result.put("name",file.getName());
						result.put("type",node.get("name").asText());
						result.put("id",ResultsUtils.newResultId());
						results.add(result);
					}
				}
			}
		}
		Utils.writeToFile(results.toString(),filepath);
	}

	public static JsonNode getResults(String userId, String name) throws CelloWebException {
		JsonNode rtn = null;
		String filepath = ResultsUtils.getResultsFile(userId,name);
		ObjectMapper mapper = new ObjectMapper();
		try {
			rtn = mapper.readTree(new File(filepath));
		} catch (IOException e) {
			throw new CelloWebException("Error with results.");
		}
		return rtn;
	}
	
	public static String getResultFilePath(String userId, String name, String resultName) {
		String rtn = "";
		rtn += ProjectUtils.getProjectDirectory(userId,name);
		rtn += Utils.getFileSeparator();
		rtn += resultName;
		return rtn;
	}

}
