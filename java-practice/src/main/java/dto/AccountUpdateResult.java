package dto;

public record AccountUpdateResult(
    String bankName,
    long interest,
    long finalBalance
) implements UpdateResult { }
