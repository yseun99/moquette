package io.moquette.interception.messages;

import java.util.function.BiConsumer;

import io.netty.handler.codec.mqtt.MqttPublishMessage;

public class InterceptPrePublishMessage extends InterceptPublishMessage {

	private final BiConsumer<InterceptPrePublishMessage, Boolean> handler;

	public InterceptPrePublishMessage(MqttPublishMessage msg, String clientID, String username,
			BiConsumer<InterceptPrePublishMessage, Boolean> handler) {
		super(msg, clientID, username);
		this.handler = handler;
	}

	public void publish(boolean autoPublish) {
		if (this.handler != null) {
			this.handler.accept(this, autoPublish);
		}
	}
}
