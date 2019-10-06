import commentjson
import os.path as path


# I'm sure there's a more pythonic way to do this but whatever
def join(path, key):
    if len(path) == 0 or len(key) == 0:
        return path+key
    return path+"."+key


def recursive_parse(obj, out, path):
    count = 0
    for key in obj:
        if type(obj[key]) is str:
                out[join(path, key)] = obj[key]
                count += 1
        elif type(obj[key] is dict):
            count += recursive_parse(obj[key], out, join(path, key))
    return count


def flatten_lang(input_dir, output_dir):
    print("Flattening lang files...", end="", flush=True)
    # TODO find all lang files
    count = 0
    key_count = 0
    with open(path.join(input_dir, "en_us.json")) as lang:
        obj = commentjson.load(lang)
        out = {}
        key_count += recursive_parse(obj, out, "")
        # TODO configurable outfile
        with open(path.join(output_dir, "en_us.json"), "w") as outfile:
            commentjson.dump(out, outfile)
            count += 1
    print("\rFlattened {} lang files with {} keys".format(count, key_count))


if __name__ == "__main__":
    # TODO command line arg for modname and directories
    flatten_lang("autogen/lang/", "src/main/resources/assets/bountifulbaubles/lang/")
