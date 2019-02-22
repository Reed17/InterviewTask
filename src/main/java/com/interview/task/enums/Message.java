package com.interview.task.enums;

/**
 * Represents api response messages.
 */
public enum Message {
    USER_WITH_EMAIL_NOT_EXIST(1, "User with current email doesn't exist!"),
    USER_ALREADY_EXISTS(2, "Email already exists!"),
    USER_NOT_FOUND(3, "User not found!"),
    WALLET_CREATION_LIMIT(4, "You can create only 3 different wallets!"),
    LOW_BALANCE(5, "Your balance is not enough to perform the transfer operation!"),
    EMPTY_AMOUNT(6, "Invalid or empty amount value!"),
    WALLET_WITH_CURRENCY_ALREADY_EXISTS(7, "You've already created a wallet with this currency!"),
    UNAUTHORIZED_ACCESS(8, "You're not authorized to access this resource!"),
    BALANCE_ADD_OPERATION_SUCCESSFUL(9, "Balance add operation successful!"),
    BALANCE_REDUCE_OPERATION_SUCCESSFUL(10, "Balance reduce operation successful!"),
    BALANCE_REPLENISH_OPERATION_SUCCESSFUL(11, "Balance replenish operation successful!"),
    MULTICURRENT_BALANCE_REPLENISH_SUCCESSFUL(12, "Multicurrent balance replenish operation successful!"),
    WALLET_SUCCESSFULLY_REMOVED(13, "Wallet was successfully removed!");

    private final Integer msgId;
    private final String msgBody;

    Message(Integer msgId, String msgBody) {
        this.msgId = msgId;
        this.msgBody = msgBody;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public String getMsgBody() {
        return msgBody;
    }
}
