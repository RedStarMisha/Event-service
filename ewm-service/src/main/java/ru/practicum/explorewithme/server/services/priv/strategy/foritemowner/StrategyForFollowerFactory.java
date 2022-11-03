package ru.practicum.explorewithme.server.services.priv.strategy.foritemowner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.subscription.SubscriptionStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class StrategyForFollowerFactory {

    private Map<SubscriptionStatus, StrategyForFollower> strategies;

    @Autowired
    public StrategyForFollowerFactory(Set<StrategyForFollower> strategy) {
        createStrategy(strategy);
    }

    public StrategyForFollower findStrategy(SubscriptionStatus state) {
        return strategies.get(state);
    }

    private void createStrategy(Set<StrategyForFollower> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(strategy -> strategies.put(strategy.getStatus(), strategy));
    }

}
