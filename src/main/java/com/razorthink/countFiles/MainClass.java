package com.razorthink.countFiles;


import org.eclipse.egit.github.core.client.GitHubClient;

import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.ArrayList;
import java.util.List;


public class MainClass {


    public static void main(String[] args) throws Exception {

        String Username = "antolivish";
        String pwd = "antoa30303";
        String localrepo = "testRepo";
        String path = "/home/antolivish/GithubRepo/testRepo/Scene 7.txt";
        String branch = "master";
        List<String> FileList = new ArrayList<String>();

        //Passing credentials
        GitHubClient client = new GitHubClient();
        client.setCredentials(Username, pwd);
        RepositoryService service = new RepositoryService(client);
        GithubOperations githubOperations = new GithubOperations();
        githubOperations.gitCloning(Username,localrepo,branch);
        githubOperations.gitRemoteRepository(service);
        githubOperations.gitRemoteBranches(service,localrepo,Username);
        FileList = githubOperations.gitListingFiles(Username,localrepo);
//        githubOperations.gitFetchContent(path);

        for(String temp : FileList){
            System.out.println(temp);
        }
    }
}


