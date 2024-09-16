package com.agriconnect.agrilink;

import java.util.List;

public interface FarmerView {
    void showUserProfile(String username);
    void showUserList(List<String> users);
    void showTenderList(List<String> tenders);
    void showError(String message);

}
