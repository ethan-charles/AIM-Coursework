package com.g52aim.project.tsp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.StringTokenizer;

import com.g52aim.project.tsp.instance.Location;
import com.g52aim.project.tsp.instance.TSPInstance;
import com.g52aim.project.tsp.interfaces.TSPInstanceInterface;
import com.g52aim.project.tsp.interfaces.TSPInstanceReaderInterface;


public class TSPInstanceReader implements TSPInstanceReaderInterface {

	@Override
	public TSPInstanceInterface readTSPInstance(Path path, Random random) {

		// TODO
		return null;
	}
}
