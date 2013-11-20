package org.hive2hive.core.process.upload.newfile;

import java.io.File;

import org.apache.log4j.Logger;
import org.hive2hive.core.IH2HFileConfiguration;
import org.hive2hive.core.file.FileManager;
import org.hive2hive.core.log.H2HLoggerFactory;
import org.hive2hive.core.model.UserProfile;
import org.hive2hive.core.network.NetworkManager;
import org.hive2hive.core.process.Process;
import org.hive2hive.core.security.UserCredentials;

/**
 * Process to upload a new file into the DHT
 * 
 * @author Nico
 * 
 */
public class NewFileProcess extends Process {

	private final static Logger logger = H2HLoggerFactory.getLogger(NewFileProcess.class);
	private final NewFileProcessContext context;

	public NewFileProcess(File file, UserCredentials credentials, NetworkManager networkManager,
			FileManager fileManager, IH2HFileConfiguration config) {
		super(networkManager);
		context = new NewFileProcessContext(this, file, credentials, fileManager, config);

		// TODO shared files not considered yet

		// 1. validate file size, split the file content, encrypt it and upload it to the DHT
		// 2. get the user profile
		// 3. get the parent meta document
		// 4. put the new meta file
		// 5. update the parent meta document
		// 6. update the user profile
		logger.debug("Adding a new file/folder to the DHT");
		setNextStep(new PutNewFileChunkStep(file, context));
	}

	/**
	 * May be used when the user profile is already existent. Thus, the step getting and decrypting the user
	 * profile can be omitted
	 * 
	 * @param userProfile the recent user profile
	 */
	public void setUserProfile(UserProfile userProfile) {
		context.setUserProfile(userProfile);
	}

	@Override
	public NewFileProcessContext getContext() {
		return context;
	}

}
