package ca.willatendo.fossilsclassic.server.entity.entities;

/*
public abstract class CustomDinosaur extends Animal {
    private static final EntityDataAccessor<Holder<Chromosome>> CHROMOSOME = SynchedEntityData.defineId(CustomDinosaur.class, FCEntityDataSerializers.CHROMOSOME.get());
    private static final EntityDataAccessor<Holder<Gene>> COSMETIC_GENE = SynchedEntityData.defineId(CustomDinosaur.class, FCEntityDataSerializers.GENE.get());

    public CustomDinosaur(EntityType<? extends CustomDinosaur> entityType, Level level) {
        super(entityType, level);
    }

    public void setChromosomeNoUpdate(Holder<Chromosome> cosmeticGene) {
        this.entityData.set(CHROMOSOME, cosmeticGene);
    }

    public void setChromosome(Holder<Chromosome> cosmeticGene) {
        this.setChromosomeNoUpdate(cosmeticGene);
        this.refreshAttributes();
        this.refreshDimensions();
        this.refreshGoals();
    }

    public Holder<Chromosome> getChromosome() {
        return this.entityData.get(CHROMOSOME);
    }

    public void setCosmeticGene(Holder<Gene> cosmeticGene) {
        this.entityData.set(COSMETIC_GENE, cosmeticGene);
    }

    public Holder<Gene> getCosmeticGene() {
        return this.entityData.get(COSMETIC_GENE);
    }

    public Holder<Gene> getBoundingBoxGene() {
        if (this.getChromosome().value() instanceof CustomChromosome customChromosome) {
            return customChromosome.boundingBoxGene();
        }
        return null;
    }

    public Holder<Gene> getBehaviorGene() {
        if (this.getChromosome().value() instanceof CustomChromosome customChromosome) {
            return customChromosome.behaviorGene();
        }
        return null;
    }

    public Holder<Gene> getAttributeGene() {
        if (this.getChromosome().value() instanceof CustomChromosome customChromosome) {
            return customChromosome.attributeGene();
        }
        return null;
    }

    public Holder<Gene> getOtherGene() {
        if (this.getChromosome().value() instanceof CustomChromosome customChromosome) {
            return customChromosome.otherGene();
        }
        return null;
    }

    public Optional<Holder<Gene>> getSoundGene() {
        if (this.getChromosome().value() instanceof CustomChromosome customChromosome) {
            return customChromosome.soundGene();
        }
        return null;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHROMOSOME, VariantUtils.getDefaultOrAny(this.registryAccess(), FCChromosomes.CITIPATI));
        builder.define(COSMETIC_GENE, VariantUtils.getDefaultOrAny(this.registryAccess(), FCGenes.COSMETICS_CITIPATI));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        HolderUtils.writeVariant(valueOutput, "chromosome", this.getChromosome());
        HolderUtils.writeVariant(valueOutput, "cosmetic", this.getCosmeticGene());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        HolderUtils.readVariant(valueInput, "chromosome", FCRegistries.CHROMOSOME).ifPresent(this::setChromosome);
        HolderUtils.readVariant(valueInput, "cosmetic", FCRegistries.GENE).ifPresent(this::setCosmeticGene);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getSoundGene().isPresent() && this.getSoundGene().get().value() instanceof SoundGene soundGene && soundGene.ambientSound().isPresent()) {
            return soundGene.ambientSound().get().value();
        }
        return super.getAmbientSound();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.getSoundGene().isPresent() && this.getSoundGene().get().value() instanceof SoundGene soundGene && soundGene.hurtSound().isPresent()) {
            return soundGene.hurtSound().get().value();
        }
        return super.getHurtSound(damageSource);
    }

    @Override
    protected SoundEvent getDeathSound() {
        if (this.getSoundGene().isPresent() && this.getSoundGene().get().value() instanceof SoundGene soundGene && soundGene.deathSound().isPresent()) {
            return soundGene.deathSound().get().value();
        }
        return super.getDeathSound();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        if (this.getOtherGene().value() instanceof OtherGene otherGene) {
            return otherGene.isFoodItem(itemStack);
        }
        return false;
    }

    public void refreshAttributes() {
        if (this.getAttributeGene().value() instanceof AttributeGene attributeGene) {
            attributeGene.initialAttributes().forEach(initialAttribute -> this.getAttribute(initialAttribute.attribute()).setBaseValue(initialAttribute.baseValue()));
        }
    }

    public void refreshGoals() {
        this.goalSelector.removeAllGoals(goal -> true);
        if (this.getBehaviorGene().value() instanceof BehaviourGene behaviourGene) {
            behaviourGene.goalSelectors().forEach(goalSelector -> this.goalSelector.addGoal(goalSelector.priority(), goalSelector.jsonBehavior().create(this)));
            behaviourGene.targetSelectors().forEach(targetSelector -> this.targetSelector.addGoal(targetSelector.priority(), targetSelector.jsonBehavior().create(this)));
        }
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    public void assignRandomCosmetic() {
        if (this.getCosmeticGene() == null) {
            Chromosome chromosome = this.getChromosome().value();
            chromosome.getCosmeticChooser().getGene(chromosome.getCosmeticGenes(), this.getRandom(), this.level().getBiome(this.blockPosition())).ifPresent(this::setCosmeticGene);
        }
    }

    public EntityDimensions getEntityDimensions(Pose pose, int size) {
        Gene gene = this.getBoundingBoxGene().value();
        if (gene instanceof BoundingBoxGene boundingBoxGene) {
            return boundingBoxGene.get(pose).toDimensions(size);
        }
        return this.dimensions;
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        if (this.getBoundingBoxGene().value() instanceof BoundingBoxGene boundingBoxGene) {
            return this.dimensions = boundingBoxGene.get(pose).toDimensions(0);
        }
        return this.dimensions;
    }

    @Override
    public void tick() {
        if (!this.isNoAi()) {
            EntityDimensions entityDimensions = this.getEntityDimensions(this.getPose(), 0);
            if (this.dimensions.width() != entityDimensions.width() || this.dimensions.height() != entityDimensions.height() || this.dimensions.eyeHeight() != entityDimensions.eyeHeight()) {
                this.refreshDimensions();
            }
        }
        super.tick();
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal instanceof CustomDinosaur customDinosaur) {
            if (!this.getChromosome().is(customDinosaur.getChromosome())) {
                return false;
            }
        }
        return super.canMate(otherAnimal);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, EntitySpawnReason entitySpawnReason, SpawnGroupData spawnGroupData) {
        this.refreshAttributes();
        this.refreshDimensions();
        this.refreshGoals();
        this.assignRandomCosmetic();

        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, entitySpawnReason, spawnGroupData);
    }
}
*/