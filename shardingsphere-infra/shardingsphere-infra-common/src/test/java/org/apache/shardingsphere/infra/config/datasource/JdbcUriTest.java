/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.config.datasource;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class JdbcUriTest {
    
    @Test
    public void assertInitJdbcUri() {
        JdbcUri jdbcUri = new JdbcUri("jdbc:mysql://127.0.0.1:3306/test_db");
        assertThat(jdbcUri.getHostname(), is("127.0.0.1"));
        assertThat(jdbcUri.getPort(), is(3306));
        assertThat(jdbcUri.getHost(), is("127.0.0.1:3306"));
        assertThat(jdbcUri.getDatabase(), is("test_db"));
        assertThat(jdbcUri.getScheme(), is("mysql"));
    }
    
    @Test
    public void assertGetParameters() {
        Map<String, String> parameters = new JdbcUri("jdbc:mysql://127.0.0.1:3306/test_db?useSSL=true&maxReconnects=30").getParameters();
        assertThat(parameters.size(), is(2));
        assertThat(parameters.get("useSSL"), is("true"));
        assertThat(parameters.get("maxReconnects"), is("30"));
    }
    
    @Test
    public void assertAppendJDBCParameters() {
        JdbcUri jdbcUri = new JdbcUri("jdbc:mysql://192.168.0.1:3306/scaling?serverTimezone=UTC&useSSL=false");
        String jdbcUrl = jdbcUri.appendParameters(ImmutableMap.<String, String>builder().put("rewriteBatchedStatements", "true").build());
        assertThat(jdbcUrl, is("jdbc:mysql://192.168.0.1:3306/scaling?rewriteBatchedStatements=true&serverTimezone=UTC&useSSL=false"));
    }
}
