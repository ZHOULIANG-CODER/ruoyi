package com.ruoyi.project.kettle.service;


import com.ruoyi.framework.aspectj.lang.annotation.DataSource;
import com.ruoyi.framework.aspectj.lang.enums.DataSourceType;
import jdk.internal.util.EnvUtils;
import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobExecutionConfiguration;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

@Service
@DataSource(DataSourceType.SLAVE)
public class KettleService {
@Value("${kettle.script.path}")
private String dirPath;

    /**
     * 执行ktr文件
     * @param filename
     * @param params
     * @return
     */
    public  void runKtr(String filename, Map<String, String> params) {
        try {
            KettleEnvironment.init();
            TransMeta tm = new TransMeta(dirPath + File.separator + filename);
            Trans trans = new Trans(tm);
            if (params != null) {
                Iterator<Map.Entry<String, String>> entries = params.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    trans.setParameterValue(entry.getKey(),entry.getValue());
                }
            }

            trans.execute(null);
            trans.waitUntilFinished();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * 执行kjb文件--设置命名参数 传递给job
     * @param filename
     * @param params
     * @return
     */

    public void runKjb(String filename, Map<String, String> params) {
        try {
            KettleEnvironment.init();
            JobMeta jm = new JobMeta(dirPath + File.separator + filename, null);

            Job job = new Job(null, jm);
            job.initializeVariablesFrom(null);
            job.clearParameters();


            if (params != null) {
                Iterator<Map.Entry<String, String>> entries = params.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, String> entry = entries.next();
                    // job.setVariable(entry.getKey(), entry.getValue());
                    // 设置命名参数
                    jm.setParameterValue(entry.getKey(), entry.getValue());
                }
            }
            jm.activateParameters();
            job.start();
            job.waitUntilFinished();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
