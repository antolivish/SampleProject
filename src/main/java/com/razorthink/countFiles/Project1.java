package com.razorthink.application.beans;

import java.io.Serializable;

/**
 * Created by rakesh on 25/2/17.
 */
public class Project implements Serializable{
    private String gitUrl;
    private String username;
    private String password;
    private String localDirectory;
    private String status;
/**
*For getting status of project
*/
    public String getStatus() {
        return status;
    }
/**
*for setting status of the project
*/

    public void setStatus(String status) {
//lakdssd
        this.status = status;
    }

    public String getLocalDirectory() {
//kalsjdhasdkl
        return localDirectory;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public String getGitUrl() {
        return gitUrl;//setter for getGitUrl
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
/*
myname
*/
//lkjslks
        return password;
    }

    public void setPassword(String password) {
/**
;alskaldk
*/
        this.password = password;
    }


public PreprocessedFile savePreprocessorInfo(UpdatePreprocessRequestUI request, User users)
			throws WebappException, WebappException {

		if (request.getPreprocessFileParamsInfo() == null) {
			throw new WebappException("Invalid preprocessor params");
		}
		if (request.getPreprocessFileParamsInfo().getColumns() == null
				|| request.getPreprocessFileParamsInfo().getColumns().isEmpty()) {
			throw new WebappException("Invalid preprocessor params: No columns specified");
		}
		int activeColCount = 0;

		for (Column c : request.getPreprocessFileParamsInfo().getColumns()) {
			if (c.isActive()) {
				activeColCount += 1;
			}
		}
		if (activeColCount == 0) {
			throw new WebappException("Invalid preprocessor params: No columns specified");
		}

            //Checking for file, if already exist
		DataSourceConfig ds = dataSourceConfigRepository
				.findOne(request.getPreprocessFileSourceInfo().getDatasourceInstanceId());
		Map<String, Object> datasourceParams = JSONUtils.parse(ds.getOperationalParams(), Map.class);
		boolean createdFile = dataConnectorUtils.copyFile(ds.getType(), datasourceParams,
				request.getPreprocessFileSourceInfo().getFilePath(),
				request.getPreprocessFileParamsInfo().getDestinationFilePath());
		if (!createdFile) {
			throw new WebappException("Couldn't create destination file"); //exception
		}

		PreprocessedFile file = getPreprocessorInfoByDestinationFilePath(
				request.getPreprocessFileSourceInfo().getDatasourceInstanceId(),
				request.getPreprocessFileParamsInfo().getDestinationFilePath(), users);
		request.getPreprocessFileParamsInfo()
				.setDestinationFilePath(request.getPreprocessFileSourceInfo().getFilePath());
//getting saved preprocessor info
		if (file == null) {
			file = new PreprocessedFile();
			file.setCreatedAt(DateTimeUtils.getCurrentTime());
			file.setFileName(new File(request.getPreprocessFileParamsInfo().getDestinationFilePath()).getName());
			file.setFilePath(request.getPreprocessFileSourceInfo().getFilePath());
			file.setPreprocessorInfo(JSONUtils.toJson(request.getPreprocessFileParamsInfo()));
			file.setSourceFilePath(request.getPreprocessFileSourceInfo().getFilePath());
			file.setDataSourceId(request.getPreprocessFileSourceInfo().getDatasourceInstanceId());
		} else {
			file.setCreatedAt(DateTimeUtils.getCurrentTime());
			file.setFileName(new File(request.getPreprocessFileParamsInfo().getDestinationFilePath()).getName());
			file.setFilePath(request.getPreprocessFileSourceInfo().getFilePath());
			file.setPreprocessorInfo(JSONUtils.toJson(request.getPreprocessFileParamsInfo()));
			file.setSourceFilePath(request.getPreprocessFileSourceInfo().getFilePath());
			file.setDataSourceId(request.getPreprocessFileSourceInfo().getDatasourceInstanceId());
			file.setUsers(users);
		}

		preprocessedFileRepository.save(file);
		return file;
	}

}
