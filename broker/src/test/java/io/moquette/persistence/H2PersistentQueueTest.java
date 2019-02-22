/*
 * Copyright (c) 2012-2018 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package io.moquette.persistence;

import io.moquette.BrokerConstants;
import org.h2.mvstore.MVStore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class H2PersistentQueueTest {

    private static MVStore mvStore;
    private H2PersistentQueue<String> sut;

    @BeforeClass
    public static void setUpBeforeCalss() {
        final File dbFile = new File(BrokerConstants.DEFAULT_PERSISTENT_PATH);
        if (dbFile.exists()) {
            dbFile.delete();
        }
    	mvStore = new MVStore.Builder()
    			.fileName(BrokerConstants.DEFAULT_PERSISTENT_PATH)
    			.autoCommitDisabled()
    			.open();
    	
    }

    @Before
    public void setUp() {
        this.sut = new H2PersistentQueue<>(mvStore, "test");
//        final File dbFile = new File(BrokerConstants.DEFAULT_PERSISTENT_PATH);
//        if (dbFile.exists()) {
//            dbFile.delete();
//        }
//        assertFalse(dbFile.exists());
//        this.mvStore = new MVStore.Builder()
//            .fileName(BrokerConstants.DEFAULT_PERSISTENT_PATH)
//            .autoCommitDisabled()
//            .open();
    }

    @After
    public void tearDown() {
    	H2PersistentQueue.dropQueue(mvStore, "test");
//        File dbFile = new File(BrokerConstants.DEFAULT_PERSISTENT_PATH);
//        if (dbFile.exists()) {
//            dbFile.delete();
//        }
//      assertFalse(dbFile.exists());
    }

    
    @AfterClass
    public static void tearDownAfterClass() {
//        File dbFile = new File(BrokerConstants.DEFAULT_PERSISTENT_PATH);
//        if (dbFile.exists()) {
//            dbFile.deleteOnExit();;
//        }
    }

    @Test
    public void testAdd() {
//        H2PersistentQueue<String> sut = new H2PersistentQueue<>(this.mvStore, "test");

        sut.add("Hello");
        sut.add("world");

        assertEquals("Hello", sut.peek());
        assertEquals("Hello", sut.peek());
        assertEquals("peek just return elements, doesn't remove them", 2, sut.size());
        
//        H2PersistentQueue.dropQueue(this.mvStore, "test");
    }

    @Test
    public void testPoll() {
//        H2PersistentQueue<String> sut = new H2PersistentQueue<>(this.mvStore, "test");
        sut.add("Hello");
        sut.add("world");

        assertEquals("Hello", sut.poll());
        assertEquals("world", sut.poll());
        assertTrue("after poll 2 elements inserted before, should be empty", sut.isEmpty());
    }

    @Ignore
    @Test
    public void testPerformance() {
//        H2PersistentQueue<String> sut = new H2PersistentQueue<>(this.mvStore, "test");

        int numIterations = 10000000;
        for (int i = 0; i < numIterations; i++) {
            sut.add("Hello");
        }
        mvStore.commit();

        for (int i = 0; i < numIterations; i++) {
            assertEquals("Hello", sut.poll());
        }

        assertTrue("should be empty", sut.isEmpty());
    }

    @Test
    public void testReloadFromPersistedState() {
//        H2PersistentQueue<String> before = new H2PersistentQueue<>(this.mvStore, "test");
    	H2PersistentQueue<String> before = sut;
        before.add("Hello");
        before.add("crazy");
        before.add("world");
        assertEquals("Hello", before.poll());
        mvStore.commit();
        mvStore.close();

        mvStore = new MVStore.Builder()
            .fileName(BrokerConstants.DEFAULT_PERSISTENT_PATH)
            .autoCommitDisabled()
            .open();

        //now reload the persisted state
        sut = new H2PersistentQueue<>(mvStore, "test");
        H2PersistentQueue<String> after = sut;

        assertEquals("crazy", after.poll());
        assertEquals("world", after.poll());
        assertTrue("should be empty", after.isEmpty());
    }
}
