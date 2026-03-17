package ca.willatendo.fossilsclassic.server.entity.entities;

/*
public class Custom extends CustomDinosaur {
    public Custom(EntityType<? extends Custom> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier baseAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.ATTACK_DAMAGE).build();
    }

    @Override
    public <T> T get(DataComponentType<? extends T> dataComponentType) {
        return dataComponentType == FCDataComponents.CHROMOSOME.get() ? Custom.castComponentValue(dataComponentType, this.getChromosome()) : super.get(dataComponentType);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter dataComponentGetter) {
        this.applyImplicitComponentIfPresent(dataComponentGetter, FCDataComponents.CHROMOSOME.get());
        super.applyImplicitComponents(dataComponentGetter);
    }

    @Override
    protected <T> boolean applyImplicitComponent(DataComponentType<T> dataComponentType, T value) {
        if (dataComponentType == FCDataComponents.CHROMOSOME.get()) {
            this.setChromosomeNoUpdate(Custom.castComponentValue(FCDataComponents.CHROMOSOME.get(), value));
            return true;
        }
        return super.applyImplicitComponent(dataComponentType, value);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand interactionHand) {
        if (!this.isAlive()) {
            return InteractionResult.PASS;
        } else {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            if (itemStack.has(FCDataComponents.CHROMOSOME.get()) && itemStack.get(FCDataComponents.CHROMOSOME.get()) == this.getChromosome() && itemStack.getItem() instanceof CustomSpawnEggItem customSpawnEggItem) {
                if (this.level() instanceof ServerLevel serverLevel) {
                    Optional<Mob> optional = customSpawnEggItem.spawnOffspringFromSpawnEgg(player, this, (EntityType<? extends Mob>) this.getType(), serverLevel, this.position(), itemStack);
                    optional.ifPresent(mob -> this.onOffspringSpawnedFromEgg(player, mob));
                    if (optional.isEmpty()) {
                        return InteractionResult.PASS;
                    }
                }

                return InteractionResult.SUCCESS_SERVER;
            }
            return super.interact(player, interactionHand);
        }
    }

    @Override
    public ItemStack getPickResult() {
        return CustomItemUtils.getSpawnEgg(this.getChromosome());
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        Custom custom = FCEntityTypes.CUSTOM.get().create(serverLevel, EntitySpawnReason.BREEDING);
        custom.setChromosome(this.getChromosome());
        return custom;
    }
}
*/
