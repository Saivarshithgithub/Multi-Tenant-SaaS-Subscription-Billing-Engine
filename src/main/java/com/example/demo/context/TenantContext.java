package com.example.demo.context;

public class TenantContext {
    
    // A thread-local variable to hold the tenant ID for the current request
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    // 1. Store the tenant ID when a request comes in
    public static void setCurrentTenant(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    // 2. Retrieve the tenant ID anytime during the request processing
    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    // 3. Clear the tenant ID when the request is done (Crucial to prevent memory leaks/data mixing!)
    public static void clear() {
        CURRENT_TENANT.remove();
    }
}