import flattenLang
import jsonGen
import addCurios


ITEMMODEL_INPUT_FILE = "autogen/itemModelGen_conf.txt"
ITEMBLOCKMODEL_INPUT_FILE = "autogen/itemBlockModelGen_conf.txt"
BLOCKLOOT_INPUT_FILE = "autogen/blockLootGen_conf.txt"
LANG_INPUT_PATH = "autogen/lang/"
CURIOS_INPUT_FILE = "autogen/curios.txt"

ITEMMODEL_PATH = "src/generated/resources/assets/{}/models/item/"
LANG_PATH = "src/generated/resources/assets/{}/lang/"
CURIOS_PATH = "src/generated/resources/data/curios/tags/items/"
BLOCKLOOT_PATH = "src/generated/resources/data/{}/loot_tables/blocks"

MOD_NAME = "bountifulbaubles"  # TODO cl args, etc.
flattenLang.flatten_lang(LANG_INPUT_PATH, LANG_PATH.format(MOD_NAME))

jsonGen.gen_item_models(ITEMMODEL_INPUT_FILE, ITEMMODEL_PATH.format(MOD_NAME), MOD_NAME)
jsonGen.gen_itemblock_models(ITEMBLOCKMODEL_INPUT_FILE, ITEMMODEL_PATH.format(MOD_NAME), MOD_NAME)
jsonGen.gen_block_loot(BLOCKLOOT_INPUT_FILE, BLOCKLOOT_PATH.format(MOD_NAME), MOD_NAME)

addCurios.add_curios(CURIOS_INPUT_FILE, CURIOS_PATH, MOD_NAME)
