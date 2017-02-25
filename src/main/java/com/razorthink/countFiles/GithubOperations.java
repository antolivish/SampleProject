package com.razorthink.countFiles;

import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by antolivish on 25/2/17.
 */
public class GithubOperations {
    ReadFile readFile = new ReadFile();

    //Getting remote repositories
    public void gitRemoteRepository(RepositoryService service) throws Exception{
        System.out.println("Remote Repository");
        System.out.println("-----------------------");
        for (Repository repo : service.getRepositories())
            System.out.println(repo.getName());
    }

    //Getting branches of specific repository
    public void gitRemoteBranches(RepositoryService service,String localrepo,String Username) throws Exception{
        System.out.println("\nRemote Branches");
        System.out.println("------------------------");
        for (Repository repo : service.getRepositories()){
            if (repo.getName().equals(localrepo)) {
                String REMOTE_URL = "https://github.com/" + Username +"/" + repo.getName() + ".git";
                Collection<Ref> refs = Git.lsRemoteRepository()
                        .setHeads(true)
                        .setTags(true)
                        .setRemote(REMOTE_URL)
                        .call();
                for (Ref ref : refs)
                    System.out.println(ref.getName());
            }
        }
    }

    //Listing Files
    public List<String> gitListingFiles(String Username,String localrepo) throws Exception{
        System.out.println("\nFile path list");
        System.out.println("------------------------");
        int count = 0;
        List<String> fileList = new ArrayList<String>();
        File dir = new File("/home/"+Username+"/GithubRepo/"+localrepo+"/");
        String[] extensions = new String[] { "txt", "jsp", "java" };
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        for (File file : files) {
            System.out.println("file: " + file.getCanonicalPath());
            fileList.add(file.getCanonicalPath());
            count++;
        }
        System.out.println("\nCount :" + count);
        return fileList;
    }

    //Fetching File Content
    public void gitFetchContent(String path) throws Exception{
        System.out.println("\nFile Content");
        System.out.println("------------------------");
        String FetchFile = readFile.readFile(path);
        System.out.println(FetchFile);
    }

    //Cloning to local repository
    public void gitCloning(String Username,String localrepo,String branch) throws Exception{
        File dir = new File("/home/"+Username+"/GithubRepo/"+localrepo+"/");
        if(dir.exists()){
            FileUtils.forceDelete(dir);
        }
        Git git = Git.cloneRepository()
                .setURI( "https://github.com/antolivish/"+localrepo+".git" )
                .setDirectory( dir )
                .setBranch(branch)
                .call();
    }
}