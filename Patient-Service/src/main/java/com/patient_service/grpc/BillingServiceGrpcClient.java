package com.patient_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);

    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    // Constructor injects gRPC server address and port
    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort
    ) {
        log.info("Connecting to Billing Service gRPC at {}:{}", serverAddress, serverPort);

        // Create a channel
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext() // Disable TLS for local/testing
                .build();

        // Create blocking stub for synchronous calls
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    // Method to create a billing account via gRPC
    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        log.info("Sending request to Billing Service: {}", request);

        // Call gRPC service
        BillingResponse response = blockingStub.createBillingAccount(request);

        log.info("Received response from Billing Service: {}", response);
        return response;
    }
}
