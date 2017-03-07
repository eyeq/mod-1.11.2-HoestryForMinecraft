package eyeq.hoestryforminecraft;

import eyeq.util.client.model.UModelCreator;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.model.gson.ItemmodelJsonFactory;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.creativetab.UCreativeTab;
import eyeq.util.item.UItemPickaxe;
import eyeq.util.item.UItemPlace;
import eyeq.util.item.UItemSword;
import eyeq.util.item.UItem;
import eyeq.util.item.UItemToolAll;
import eyeq.util.item.UItemAxe;
import eyeq.util.item.crafting.FuelHandler;
import eyeq.util.item.crafting.UCraftingManager;
import eyeq.util.oredict.CategoryTypes;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import static eyeq.hoestryforminecraft.HoestryForMinecraft.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class HoestryForMinecraft {
    public static final String MOD_ID = "eyeq_hoestryforminecraft";

    @Mod.Instance(MOD_ID)
    public static HoestryForMinecraft instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Item hoePorkchop;
    public static final CreativeTabs TAB_HOESTRY = new UCreativeTab("HoestryForMinecraft", () -> new ItemStack(hoePorkchop));

    public static Item hoeDirt;
    public static Item hoeHotStone;
    public static Item hoeObsidian;
    public static Item hoeRedstone;
    public static Item hoeLapis;
    public static Item hoeCoal;
    public static Item hoeFlint;
    public static Item hoeEmerald;
    public static Item hoeWater;
    public static Item hoeLava;
    public static Item hoeLog;
    public static Item hoeGravel;
    public static Item hoeSand;
    public static Item hoeLeaves;
    public static Item hoeWool;
    public static Item hoeBrick;
    public static Item hoeTnt;
    public static Item hoeLadder;
    public static Item hoeSnow;
    public static Item hoeIce;
    public static Item hoeDiamondBlock;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        Item.ToolMaterial toolMaterialDirt = EnumHelper.addToolMaterial("dirt", 2, 15, 32.0F, 0.0F, 100);
        Item.ToolMaterial toolMaterialHotStone = EnumHelper.addToolMaterial("hotstone", 2, 255, 4.0F, 0.0F, 100);
        Item.ToolMaterial toolMaterialObsidian = EnumHelper.addToolMaterial("obsidian", 1, 25000, 32.0F, 0.0F, 100);
        Item.ToolMaterial toolMaterialRedstone = EnumHelper.addToolMaterial("redstone", 2, 5, 4.0F, 0.0F, 100);
        Item.ToolMaterial toolMaterialLapis = EnumHelper.addToolMaterial("lapis", 3, 10, 100.0F, 0.0F, 100);
        Item.ToolMaterial toolMaterialCoal = EnumHelper.addToolMaterial("coal", 3, 256, 0.0F, 2.0F, 100);
        Item.ToolMaterial toolMaterialEmerald = EnumHelper.addToolMaterial("emerald", 2, 512, 0.0F, 3.0F, 100);
        Item.ToolMaterial toolMaterialLog = EnumHelper.addToolMaterial("log", 3, 512, 5.0F, 0.0F, 100);
        Item.ToolMaterial toolMaterialBrick = EnumHelper.addToolMaterial("brick", 3, 1000, 4.0F, 0.0F, 100);
        Item.ToolMaterial toolMaterialSnow = EnumHelper.addToolMaterial("snow", 3, 100000, 1.0F, 0.0F, 100);
        Item.ToolMaterial toolMaterialDiamondBlock = EnumHelper.addToolMaterial("diamond_block", 100, 512, 30.0F, 0.0F, 100);

        toolMaterialDirt.setRepairItem(new ItemStack(Blocks.DIRT));
        toolMaterialHotStone.setRepairItem(new ItemStack(Blocks.STONE));
        toolMaterialObsidian.setRepairItem(new ItemStack(Blocks.OBSIDIAN));
        toolMaterialRedstone.setRepairItem(new ItemStack(Items.REDSTONE));
        toolMaterialLapis.setRepairItem(new ItemStack(Items.DYE, 1, 4));
        toolMaterialCoal.setRepairItem(new ItemStack(Items.COAL));
        toolMaterialEmerald.setRepairItem(new ItemStack(Items.EMERALD));
        toolMaterialLog.setRepairItem(new ItemStack(Blocks.PLANKS));
        toolMaterialBrick.setRepairItem(new ItemStack(Blocks.BRICK_BLOCK));
        toolMaterialSnow.setRepairItem(new ItemStack(Blocks.SNOW));
        toolMaterialDiamondBlock.setRepairItem(new ItemStack(Blocks.DIAMOND_BLOCK));

        hoeLadder = new UItem(true).setPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 400, 10), 1.0F).setMaxDamage(10).setUnlocalizedName("hoeLadder").setCreativeTab(TAB_HOESTRY);

        hoeDirt = new UItemAxe(toolMaterialDirt).setUnlocalizedName("hoeDirt").setCreativeTab(TAB_HOESTRY);
        hoeLog = new UItemAxe(toolMaterialLog).setUnlocalizedName("hoeLog").setCreativeTab(TAB_HOESTRY);

        hoeHotStone = new ItemPickaxe(toolMaterialHotStone) {}.setUnlocalizedName("hoeHotstone").setCreativeTab(TAB_HOESTRY);
        hoeObsidian = new UItemPickaxe(toolMaterialObsidian).setUseItemDamage(30000).setPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 300, 255), 1.0F).setUnlocalizedName("hoeObsidian").setCreativeTab(TAB_HOESTRY);
        hoeRedstone = new UItemPickaxe(toolMaterialRedstone).setPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 255), 1.0F).setUnlocalizedName("hoeRedstone").setCreativeTab(TAB_HOESTRY);
        hoeLapis = new ItemPickaxe(toolMaterialLapis) {}.setUnlocalizedName("hoeLapis").setCreativeTab(TAB_HOESTRY);

        hoeSnow = new ItemSpade(toolMaterialSnow).setUnlocalizedName("hoeSnow").setCreativeTab(TAB_HOESTRY);

        hoeCoal = new UItemSword(toolMaterialCoal).setAttackFire(20, 1.0F).setUnlocalizedName("hoeCoal").setCreativeTab(TAB_HOESTRY);
        hoeEmerald = new ItemSword(toolMaterialEmerald).setUnlocalizedName("hoeEmerald").setCreativeTab(TAB_HOESTRY);

        hoeFlint = new ItemFlintAndSteel().setFull3D().setUnlocalizedName("hoeFlint").setCreativeTab(TAB_HOESTRY);
        hoeWater = new UItemPlace(Blocks.FLOWING_WATER.getDefaultState(), true).setUnlocalizedName("hoeWater").setCreativeTab(TAB_HOESTRY);
        hoeLava = new UItemPlace(Blocks.FLOWING_LAVA.getDefaultState(), true).setUnlocalizedName("hoeLava").setCreativeTab(TAB_HOESTRY);
        hoeTnt = new UItemPlace(Blocks.TNT.getDefaultState(), true).setUnlocalizedName("hoeTnt").setCreativeTab(TAB_HOESTRY);
        hoeIce = new UItemPlace(Blocks.ICE.getDefaultState(), true).setUnlocalizedName("hoeIce").setCreativeTab(TAB_HOESTRY);

        hoeGravel = new ItemFood(10, false).setAlwaysEdible().setPotionEffect(new PotionEffect(MobEffects.HASTE, 1200, 10), 1.0F).setFull3D().setMaxStackSize(1).setUnlocalizedName("hoeGravel").setCreativeTab(TAB_HOESTRY);
        hoeSand = new ItemFood(5, true).setAlwaysEdible().setPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 0), 1.0F).setFull3D().setMaxStackSize(1).setUnlocalizedName("hoeSand").setCreativeTab(TAB_HOESTRY);
        hoePorkchop = new ItemFood(10, true) {
            @Override
            public boolean hasEffect(ItemStack item) {
                return true;
            }
        }.setAlwaysEdible().setPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 300, 0), 1.0F).setFull3D().setMaxStackSize(1).setUnlocalizedName("hoePorkchop").setCreativeTab(TAB_HOESTRY);

        hoeLeaves = new ItemShears() {
            @Override
            public float getStrVsBlock(ItemStack stack, IBlockState state) {
                return state.getMaterial() == Material.LEAVES ? super.getStrVsBlock(stack, state) : 1.0F;
            }
        }.setFull3D().setMaxDamage(64).setUnlocalizedName("hoeLeaves").setCreativeTab(TAB_HOESTRY);
        hoeWool = new ItemShears() {
            public float getStrVsBlock(ItemStack stack, IBlockState state) {
                Block block = state.getBlock();
                return block == Blocks.WEB || block == Blocks.WOOL ? super.getStrVsBlock(stack, state) : 1.0F;
            }
        }.setFull3D().setMaxDamage(64).setUnlocalizedName("hoeWool").setCreativeTab(TAB_HOESTRY);

        hoeBrick = new UItemToolAll(1.0F, -2.8F, toolMaterialBrick, 5.0F).setUnlocalizedName("hoeBrick").setCreativeTab(TAB_HOESTRY);
        hoeDiamondBlock = new UItemToolAll(198.0F, 0, toolMaterialDiamondBlock, 40.0F).addEnchantmentData(new EnchantmentData(Enchantments.SILK_TOUCH, 1)).setUnlocalizedName("hoeDiamondBlock").setCreativeTab(TAB_HOESTRY);

        GameRegistry.register(hoeDirt, resource.createResourceLocation("dirt_hoe"));
        GameRegistry.register(hoeHotStone, resource.createResourceLocation("hotstone_hoe"));
        GameRegistry.register(hoeObsidian, resource.createResourceLocation("obsidian_hoe"));
        GameRegistry.register(hoeRedstone, resource.createResourceLocation("redstone_hoe"));
        GameRegistry.register(hoeLapis, resource.createResourceLocation("lapis_hoe"));
        GameRegistry.register(hoeCoal, resource.createResourceLocation("coal_hoe"));
        GameRegistry.register(hoeFlint, resource.createResourceLocation("flint_hoe"));
        GameRegistry.register(hoeEmerald, resource.createResourceLocation("emerald_hoe"));
        GameRegistry.register(hoeWater, resource.createResourceLocation("water_hoe"));
        GameRegistry.register(hoeLava, resource.createResourceLocation("lava_hoe"));
        GameRegistry.register(hoeLog, resource.createResourceLocation("log_hoe"));
        GameRegistry.register(hoeGravel, resource.createResourceLocation("gravel_hoe"));
        GameRegistry.register(hoeSand, resource.createResourceLocation("sand_hoe"));
        GameRegistry.register(hoeLeaves, resource.createResourceLocation("leaves_hoe"));
        GameRegistry.register(hoeWool, resource.createResourceLocation("wool_hoe"));
        GameRegistry.register(hoeBrick, resource.createResourceLocation("brick_hoe"));
        GameRegistry.register(hoeTnt, resource.createResourceLocation("tnt_hoe"));
        GameRegistry.register(hoeLadder, resource.createResourceLocation("ladder_hoe"));
        GameRegistry.register(hoeSnow, resource.createResourceLocation("snow_hoe"));
        GameRegistry.register(hoeIce, resource.createResourceLocation("ice_hoe"));
        GameRegistry.register(hoePorkchop, resource.createResourceLocation("porkchop_hoe"));
        GameRegistry.register(hoeDiamondBlock, resource.createResourceLocation("diamond_block_hoe"));

        UOreDictionary.registerOre(CategoryTypes.COOKED, "hoe", hoePorkchop);
    }

    public static void addRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(hoeDirt),
                Blocks.DIRT, Blocks.DIRT, Blocks.DIRT);
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeHotStone), UOreDictionary.OREDICT_STONE));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeObsidian), UOreDictionary.OREDICT_OBSIDIAN));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeRedstone), UOreDictionary.OREDICT_REDSTONE));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeLapis), UOreDictionary.OREDICT_LAPIS));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeCoal), Items.COAL));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeFlint), Items.FLINT));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeEmerald), UOreDictionary.OREDICT_EMERALD));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeWater), Items.WATER_BUCKET));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeLava), Items.LAVA_BUCKET));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeLog), UOreDictionary.OREDICT_LOG));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeGravel), UOreDictionary.OREDICT_GRAVEL));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeSand), UOreDictionary.OREDICT_SAND));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeLeaves), UOreDictionary.OREDICT_LEAVES));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeWool), Blocks.WOOL));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeBrick), Blocks.BRICK_BLOCK));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeTnt), Blocks.TNT));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeLadder), Blocks.LADDER));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeSnow), Blocks.SNOW));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeIce), Blocks.ICE));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoePorkchop), Items.COOKED_PORKCHOP));
        GameRegistry.addRecipe(UCraftingManager.getRecipeHoe(new ItemStack(hoeDiamondBlock), UOreDictionary.OREDICT_DIAMOND_BLOCK));

        GameRegistry.registerFuelHandler(new FuelHandler(hoeCoal, 800));
        GameRegistry.registerFuelHandler(new FuelHandler(hoeLava, 10000));
        GameRegistry.registerFuelHandler(new FuelHandler(hoeLog, 200));
        GameRegistry.registerFuelHandler(new FuelHandler(hoeLadder, 200));
    }

    @SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(hoeDirt);
        UModelLoader.setCustomModelResourceLocation(hoeHotStone);
        UModelLoader.setCustomModelResourceLocation(hoeObsidian);
        UModelLoader.setCustomModelResourceLocation(hoeRedstone);
        UModelLoader.setCustomModelResourceLocation(hoeLapis);
        UModelLoader.setCustomModelResourceLocation(hoeCoal);
        UModelLoader.setCustomModelResourceLocation(hoeFlint);
        UModelLoader.setCustomModelResourceLocation(hoeEmerald);
        UModelLoader.setCustomModelResourceLocation(hoeWater);
        UModelLoader.setCustomModelResourceLocation(hoeLava);
        UModelLoader.setCustomModelResourceLocation(hoeLog);
        UModelLoader.setCustomModelResourceLocation(hoeGravel);
        UModelLoader.setCustomModelResourceLocation(hoeSand);
        UModelLoader.setCustomModelResourceLocation(hoeLeaves);
        UModelLoader.setCustomModelResourceLocation(hoeWool);
        UModelLoader.setCustomModelResourceLocation(hoeBrick);
        UModelLoader.setCustomModelResourceLocation(hoeTnt);
        UModelLoader.setCustomModelResourceLocation(hoeLadder);
        UModelLoader.setCustomModelResourceLocation(hoeSnow);
        UModelLoader.setCustomModelResourceLocation(hoeIce);
        UModelLoader.setCustomModelResourceLocation(hoePorkchop);
        UModelLoader.setCustomModelResourceLocation(hoeDiamondBlock);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-HoestryForMinecraft");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, TAB_HOESTRY, "HoestryForMinecraft");
        language.register(LanguageResourceManager.EN_US, TAB_HOESTRY, "ほえすとりーまいんくらふと");

        language.register(LanguageResourceManager.EN_US, hoeDirt, "Dirt Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeDirt, "土のクワ");
        language.register(LanguageResourceManager.EN_US, hoeHotStone, "Hot Stone Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeHotStone, "焼き石のクワ");
        language.register(LanguageResourceManager.EN_US, hoeObsidian, "Obsidian Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeObsidian, "黒曜石のクワ");
        language.register(LanguageResourceManager.EN_US, hoeRedstone, "Redstone Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeRedstone, "レッドストーンのクワ");
        language.register(LanguageResourceManager.EN_US, hoeLapis, "Lapis Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeLapis, "ラピスラズリのクワ");
        language.register(LanguageResourceManager.EN_US, hoeCoal, "Coal Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeCoal, "石炭のクワ");
        language.register(LanguageResourceManager.EN_US, hoeFlint, "Flint Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeFlint, "火打ち石のクワ");
        language.register(LanguageResourceManager.EN_US, hoeEmerald, "Emerald Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeEmerald, "エメラルドのクワ");
        language.register(LanguageResourceManager.EN_US, hoeWater, "Water Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeWater, "水のクワ");
        language.register(LanguageResourceManager.EN_US, hoeLava, "Lave Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeLava, "溶岩のクワ");
        language.register(LanguageResourceManager.EN_US, hoeLog, "Log Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeLog, "原木のクワ");
        language.register(LanguageResourceManager.EN_US, hoeGravel, "Gravel Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeGravel, "砂利のクワ");
        language.register(LanguageResourceManager.EN_US, hoeSand, "Sand Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeSand, "砂のクワ");
        language.register(LanguageResourceManager.EN_US, hoeLeaves, "Leaves Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeLeaves, "葉っぱのクワ");
        language.register(LanguageResourceManager.EN_US, hoeWool, "Cloth Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeWool, "羊毛のクワ");
        language.register(LanguageResourceManager.EN_US, hoeBrick, "Bricks Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeBrick, "煉瓦のクワ");
        language.register(LanguageResourceManager.EN_US, hoeTnt, "TNT Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeTnt, "爆裂のクワ");
        language.register(LanguageResourceManager.EN_US, hoeLadder, "Ladder Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeLadder, "はしごのクワ");
        language.register(LanguageResourceManager.EN_US, hoeSnow, "Snow Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeSnow, "雪のクワ");
        language.register(LanguageResourceManager.EN_US, hoeIce, "Absolute Zero! Ice Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeIce, "絶対零度！ 氷のクワ");
        language.register(LanguageResourceManager.EN_US, hoePorkchop, "PorkCHoe");
        language.register(LanguageResourceManager.JA_JP, hoePorkchop, "豚ニクワ");
        language.register(LanguageResourceManager.EN_US, hoeDiamondBlock, "The Strongest Diamond Hoe");
        language.register(LanguageResourceManager.JA_JP, hoeDiamondBlock, "最強のダイヤモンドのクワ");

        ULanguageCreator.createLanguage(project, MOD_ID, language);

        UModelCreator.createItemJson(project, hoeDirt, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeHotStone, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeObsidian, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeRedstone, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeLapis, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeCoal, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeFlint, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeEmerald, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeWater, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeLava, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeLog, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeGravel, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeSand, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeLeaves, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeWool, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeBrick, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeTnt, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeLadder, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeSnow, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeIce, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoePorkchop, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
        UModelCreator.createItemJson(project, hoeDiamondBlock, ItemmodelJsonFactory.ItemmodelParent.HANDHELD);
    }
}
