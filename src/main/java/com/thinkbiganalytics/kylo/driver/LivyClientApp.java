package com.thinkbiganalytics.kylo.driver;

/*
 * Licensed to Cloudera, Inc. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  Cloudera, Inc. licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.thinkbiganalytics.kylo.driver.jobs.PiJob;
import org.apache.livy.LivyClient;
import org.apache.livy.LivyClientBuilder;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * To run:
 * Configure and start Livy
 * 1. Download Livy and unpack
 * $  wget http://apache.cs.utah.edu/incubator/livy/0.5.0-incubating/livy-0.5.0-incubating-bin.zip
 * $  unzip
 * 2. Create conf file
 * $  cd livy-0.5.0-incubating-bin/conf
 * $  cp livy.conf.template livy.conf
 * 3. Add the following config to livy.conf
 * livy.file.local-dir-whitelist =/home/harsch/.livy-sessions/
 * 4. Create log conf file
 * $  cp log4j.properties.template log4j.properties
 * Run the this App with runPi.sh
 * $  use runPi.sh
 */
public class LivyClientApp {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: see runPi.sh");
            System.exit(-1);
        }

        LivyClient client = new LivyClientBuilder(true)
                .setURI(new URI(args[0]))
                .build();

        try {
            System.out.println("Uploading livy-client-app jar to the SparkContext...");
            for (String s : System.getProperty("java.class.path").split(File.pathSeparator)) {
                if (new File(s).getName().startsWith("livy-client-app")) {
                    client.uploadJar(new File(s)).get();
                    break;
                }
            }

            final int slices = Integer.parseInt(args[1]);
            double pi = client.submit(new PiJob(slices)).get();

            System.out.println("Pi is roughly " + pi);
        } finally {
            client.stop(true);
        }
    }
}