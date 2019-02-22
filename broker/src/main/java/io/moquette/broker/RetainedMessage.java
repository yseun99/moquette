package io.moquette.broker;

import io.netty.handler.codec.mqtt.MqttQoS;

import java.io.Serializable;

public class RetainedMessage implements Serializable{

	private static final long serialVersionUID = -7389619635378817369L;

	private final MqttQoS qos;
    private final byte[] payload;

    public RetainedMessage(MqttQoS qos, byte[] payload) {
        this.qos = qos;
        this.payload = payload;
    }

    public MqttQoS qosLevel() {
        return qos;
    }

    public byte[] getPayload() {
        return payload;
    }
}
