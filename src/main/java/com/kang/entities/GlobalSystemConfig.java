package com.kang.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GlobalSystemConfig {
	
	private String systemConfigPath = "SystemConfig.obj";
	
	private static GlobalSystemConfig globalSystemConfig = new GlobalSystemConfig();
	
	private SystemConfig systemConfig = null;
	
	/**
	 * 读取配置，生成单例全局配置。
	 */
	private GlobalSystemConfig() {
		File configFile = new File(systemConfigPath);
		if (configFile.exists()) {
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new FileInputStream(configFile));
				this.systemConfig = (SystemConfig) ois.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @return globalSystemConfig
	 */
	public static GlobalSystemConfig getInstance() {
		return globalSystemConfig;
	}
	
	public SystemConfig getSystemConfig() {
		return this.systemConfig;
	}

	public String getSystemConfigPath() {
		return systemConfigPath;
	}
	/**
	 * 保存系统设置
	 * @param systemConfig
	 */
	public boolean saveSystemConfig() {
		//Write Obj to File
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(systemConfigPath));
            oos.writeObject(systemConfig);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
}
