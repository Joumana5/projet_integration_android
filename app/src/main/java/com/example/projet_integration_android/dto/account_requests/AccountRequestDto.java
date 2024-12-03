package com.example.projet_integration_android.dto.account_requests;

public class AccountRequestDto {
    private int accountId;
    private boolean accountAccepted;
    private String rejectionReason;

    public int getAccountId() {
        return accountId;
    }

    public boolean isAccountAccepted() {
        return accountAccepted;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAccountAccepted(boolean accountAccepted) {
        this.accountAccepted = accountAccepted;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
