package cursedflames.bountifulbaubles.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.network.PacketHandler;
import cursedflames.bountifulbaubles.network.PacketHandler.HandlerIds;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.Property.Type;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Config class that handles synchronization from server to client.
 * 
 * @author CursedFlames
 *
 */
public class Config {
//	public static Map<String, Config> modConfigs = new HashMap<>();
	public static Config config;

	final String version;
	Configuration configuration;
	Logger logger;
	boolean configReset = false;
	boolean isDedicatedServer;

	/** Properties that are only on the logical client. */
	Map<String, Property> clientProps = new HashMap<>();
	/**
	 * The local values of properties that are synchronized from the logical
	 * server.
	 */
	Map<String, Property> localProps = new HashMap<>();
	/**
	 * Properties from the logical server that have been synchronized to the
	 * logical client.
	 */
	Map<String, Property> syncedProps = new HashMap<>();
	/** Properties that are only on the logical server. */
	Map<String, Property> serverProps = new HashMap<>();

	public enum EnumPropSide {
		CLIENT,
		SERVER,
		SYNCED;
	}

	public Map<String, Property> getLocalPropMap(EnumPropSide side) {
		if (side==EnumPropSide.CLIENT) {
			return clientProps;
		} else if (side==EnumPropSide.SERVER) {
			return serverProps;
		} else {
			return localProps;
		}
	}

	public Map<String, Property> getSyncedPropMap() {
		if (isDedicatedServer) {
			return localProps;
		} else {
			return syncedProps;
		}
	}

	public Property getSyncedProperty(String key) {
		if (syncedProps.containsKey(key))
			return syncedProps.get(key);
		if (!localProps.containsKey(key))
			return null;
		Property copy = copyProperty(localProps.get(key));
		syncedProps.put(key, copy);
		return copy;
	}

	public Config(String version, Logger logger) {
		this.version = version;
		this.logger = logger;
		config = this;
//		logger.info(modConfigs.keySet().size());
//		logger.info(modConfigs.get(modId)==null);
//		logger.info(this==null);
		isDedicatedServer = FMLCommonHandler.instance().getSide()==Side.SERVER;
	}

	public void preInit(FMLPreInitializationEvent e) {
		File directory = e.getModConfigurationDirectory();
		File configFile = new File(directory.getPath(), BountifulBaubles.MODID+".cfg");
		configuration = new Configuration(configFile, version);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void postInit(FMLPostInitializationEvent e) {
		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	private Property copyProperty(Property prop) {
		if (prop==null)
			return null;
		Type type = prop.getType();
		Property prop1 = new Property(prop.getName(), prop.getString(), type, prop.getValidValues(),
				prop.getLanguageKey());
		prop1.setComment(prop.getComment());
		return prop1;
	}

	public void readConfig() {
		try {
			configuration.load();
		} catch (Exception e) {
			logger.error("Failed to load config: ", e);
		} finally {
			if (configuration.hasChanged()||configReset) {
				configuration.save();
			}
		}
	}

	public Property addPropInt(String name, String category, String desc, int defaultVal,
			EnumPropSide side) {
		Property prop = configuration.get(category, name, defaultVal);
		prop.setComment(desc);
		getLocalPropMap(side).put(name, prop);
		return prop;
	}

	public Property addPropInt(String name, String category, String desc, int defaultVal,
			EnumPropSide side, int min, int max) {
		Property prop = addPropInt(name, category, desc, defaultVal, side);
		prop.setMinValue(min);
		prop.setMaxValue(max);
		return prop;
	}

	public Property addPropDouble(String name, String category, String desc, double defaultVal,
			EnumPropSide side) {
		Property prop = configuration.get(category, name, defaultVal);
		prop.setComment(desc);
		getLocalPropMap(side).put(name, prop);
		return prop;
	}

	public Property addPropDouble(String name, String category, String desc, double defaultVal,
			EnumPropSide side, double min, double max) {
		Property prop = addPropDouble(name, category, desc, defaultVal, side);
		prop.setMinValue(min);
		prop.setMaxValue(max);
		return prop;
	}

	public Property addPropBoolean(String name, String category, String desc, boolean defaultVal,
			EnumPropSide side) {
		Property prop = configuration.get(category, name, defaultVal);
		prop.setComment(desc);
		getLocalPropMap(side).put(name, prop);
		return prop;
	}

	public Property addPropString(String name, String category, String desc, String defaultVal,
			EnumPropSide side) {
		Property prop = configuration.get(category, name, defaultVal);
		prop.setComment(desc);
		getLocalPropMap(side).put(name, prop);
		return prop;
	}

	public Property addPropStringList(String name, String category, String desc,
			String[] defaultVal, EnumPropSide side) {
		Property prop = configuration.get(category, name, defaultVal);
		prop.setComment(desc);
		getLocalPropMap(side).put(name, prop);
		return prop;
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.player.world.isRemote) {
			logger.info("Attempting to sync config to player "+event.player.getName());
			NBTTagCompound tag = getSyncTag();
			NBTTagCompound tag1 = new NBTTagCompound();
			tag1.setTag("values", tag);
			PacketHandler.INSTANCE.sendTo(new NBTPacket(tag1, HandlerIds.SYNC_SERVER_DATA.id),
					(EntityPlayerMP) event.player);
		}
	}

	public NBTTagCompound getSyncTag() {
		// TODO support for lists
		NBTTagCompound tag = new NBTTagCompound();
		for (String key : localProps.keySet()) {
			Property prop = localProps.get(key);
			Type type = prop.getType();
			if (type==Type.INTEGER) {
				tag.setInteger(key, prop.getInt());
			} else if (type==Type.DOUBLE) {
				tag.setDouble(key, prop.getDouble());
			} else if (type==Type.BOOLEAN) {
				tag.setBoolean(key, prop.getBoolean());
			} else if (type==Type.STRING) {
				tag.setString(key, prop.getString());
			}
		}
		return tag;
	}

	public void loadSyncTag(NBTTagCompound tag) {
		for (String key : tag.getKeySet()) {
			Property prop = localProps.get(key);
			if (prop==null) {
				logger.warn("Attempted to load nonexistent config value \""+key+"\"");
				continue;
			}
			Type type = prop.getType();
			Property propSynced = getSyncedProperty(key);
			if (type==Type.INTEGER) {
				propSynced.setValue(tag.getInteger(key));
			} else if (type==Type.DOUBLE) {
				propSynced.setValue(tag.getDouble(key));
			} else if (type==Type.BOOLEAN) {
				propSynced.setValue(tag.getBoolean(key));
			} else if (type==Type.STRING) {
				propSynced.setValue(tag.getString(key));
			}
//			logger.info(key+" "+prop.getString()+" "+propSynced.getString());
		}
	}
}
