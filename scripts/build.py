import flattenLang
import genItemModels
import addCurios


ITEMMODEL_INPUT_FILE = "autogen/itemModelGen_conf.txt"
LANG_INPUT_PATH = "autogen/lang/"
CURIOS_INPUT_FILE = "autogen/curios.txt"

ITEMMODEL_PATH = "src/main/resources/assets/{}/models/item/"
LANG_PATH = "src/main/resources/assets/{}/lang/"
CURIOS_PATH = "src/main/resources/data/curios/tags/items/"

MOD_NAME = "bountifulbaubles"  # TODO cl args, etc.
flattenLang.flatten_lang(LANG_INPUT_PATH, LANG_PATH.format(MOD_NAME))

genItemModels.gen_item_models(ITEMMODEL_INPUT_FILE, ITEMMODEL_PATH.format(MOD_NAME), MOD_NAME)

addCurios.add_curios(CURIOS_INPUT_FILE, CURIOS_PATH, MOD_NAME)
