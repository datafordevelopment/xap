/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * @(#)ClusterPolicyTest.java   Jan 1, 2008
 *
 * Copyright 2007 GigaSpaces Technologies Inc.
 */
package com.j_spaces.core.cluster;

import com.gigaspaces.cluster.loadbalance.LoadBalancingPolicy;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author kimchy
 */
@com.gigaspaces.api.InternalApi
public class LoadBalancingPolicyDescTest extends TestCase {

    private LoadBalancingPolicy.LoadBalancingPolicyDescription full;
    private LoadBalancingPolicy.LoadBalancingPolicyDescription empty;
    private ExternalizableTestHelper helper;

    protected void setUp() throws Exception {
        helper = new ExternalizableTestHelper();
        full = helper.fill(new LoadBalancingPolicy.LoadBalancingPolicyDescription());
        empty = new LoadBalancingPolicy.LoadBalancingPolicyDescription();
    }

    /**
     * Test read and write empty ClusterPolicy object
     */
    public void testWriteReadEmpty() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(empty);
        ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
        LoadBalancingPolicy.LoadBalancingPolicyDescription copy = (LoadBalancingPolicy.LoadBalancingPolicyDescription) is.readObject();
        is.close();
        os.close();
        assertTrue(helper.areEquals(empty, copy));
    }

    /**
     * Test read and write full ClusterPolicy object
     */
    public void testWriteReadFull() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream(0);
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(full);
        ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
        LoadBalancingPolicy.LoadBalancingPolicyDescription copy = (LoadBalancingPolicy.LoadBalancingPolicyDescription) is.readObject();
        is.close();
        os.close();
        assertTrue(helper.areEquals(full, copy));
    }
}