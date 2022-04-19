package com.theswirlingvoid.polarmachinery;

import java.util.stream.Collectors;

import com.mojang.logging.LogUtils;
import com.theswirlingvoid.polarmachinery.block.registry.BlockRegistries;
import com.theswirlingvoid.polarmachinery.blockentity.registry.BlockEntityRegistries;

import org.slf4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("polarmachinery")
public class PolarMachinery
{
    public static final String MOD_ID = "polarmachinery";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PolarMachinery()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);
        
        MinecraftForge.EVENT_BUS.register(this);

        BlockRegistries.BLOCKS.register(eventBus);
        BlockRegistries.ITEMS.register(eventBus);
        BlockEntityRegistries.BLOCK_ENTITIES.register(eventBus);
        
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // preinit code
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        // InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("Oh look! The server's starting!");
    }

    public void onBlockBreak(BlockEvent.NeighborNotifyEvent event) {

    }
    
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

    }

}
