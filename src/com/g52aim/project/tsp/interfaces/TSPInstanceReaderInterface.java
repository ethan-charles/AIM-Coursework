package com.g52aim.project.tsp.interfaces;

import java.nio.file.Path;
import java.util.Random;

public interface TSPInstanceReaderInterface {

	public TSPInstanceInterface readTSPInstance(Path path, Random random);
}
