package cuchaz.enigma.command;

import cuchaz.enigma.throwables.MappingParseException;
import cuchaz.enigma.translation.mapping.EntryMapping;
import cuchaz.enigma.translation.mapping.MappingFileNameFormat;
import cuchaz.enigma.translation.mapping.MappingSaveParameters;
import cuchaz.enigma.translation.mapping.tree.EntryTree;
import cuchaz.enigma.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InvertMappingsCommand extends Command {
    public InvertMappingsCommand() {
        super("invert-mappings");
    }

    @Override
    public String getUsage() {
        return "<source-format> <source> <result-format> <result>";
    }

    @Override
    public boolean isValidArgument(int length) {
        return length == 4;
    }

    @Override
    public void run(String... args) throws IOException, MappingParseException {
        MappingSaveParameters saveParameters = new MappingSaveParameters(MappingFileNameFormat.BY_DEOBF);

        EntryTree<EntryMapping> source = MappingCommandsUtil.read(args[0], Paths.get(args[1]), saveParameters);
        EntryTree<EntryMapping> result = MappingCommandsUtil.invert(source);

        Path output = Paths.get(args[3]);
        Utils.delete(output);
        MappingCommandsUtil.write(result, args[2], output, saveParameters);
    }
}
