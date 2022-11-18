package ru.practicum.explorewithme.server.exceptions.notfound;

public class SubscriptionNotFoundException extends EntityNotFoundException {

    public SubscriptionNotFoundException(long subscriptionId) {
        super("Subscription", subscriptionId);
    }

    public SubscriptionNotFoundException(String title) {
        super("Subscription", title);
    }

    @Override
    public String getEntityType() {
        return "Subscription";
    }
}
