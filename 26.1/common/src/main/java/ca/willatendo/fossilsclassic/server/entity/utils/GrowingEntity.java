package ca.willatendo.fossilsclassic.server.entity.utils;

public interface GrowingEntity {
    void setGrowthStage(int growthStage, boolean resetHealth);

    int getGrowthStage();

    int getMaxGrowthStage();
}
