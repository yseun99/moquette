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

package io.moquette.interception.messages;

import io.moquette.broker.subscriptions.Topic;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

public class InterceptPublishMessage extends InterceptAbstractMessage<MqttPublishMessage> {

    private final String clientID;
    private final String username;
	private final Topic topic;

    public InterceptPublishMessage(MqttPublishMessage msg, String clientID, String username) {
        super(msg);
        this.clientID = clientID;
        this.username = username;
		this.topic = new Topic(msg.variableHeader().topicName());
    }

    public String getTopicName() {
		return this.topic.toString();
	}

	public Topic getTopic() {
		return this.topic;
    }

    public ByteBuf getPayload() {
		return this.msg.payload();
	}

	public void setPayload(ByteBuf content) {
		this.msg = this.msg.replace(content);
	}

	public MqttPublishMessage getMqttMessage() {
		return this.msg;
    }

    public String getClientID() {
        return clientID;
    }

    public String getUsername() {
        return username;
    }
}
