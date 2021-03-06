package jsettlers.common.buildings.loader;

import jsettlers.common.buildings.jobs.EBuildingJobType;
import jsettlers.common.material.EMaterialType;
import jsettlers.common.material.ESearchType;
import jsettlers.common.movable.EDirection;

import org.xml.sax.Attributes;

public class JobElementWrapper implements BuildingJobData {
	private static final String SUCCESSJOB = "successjob";
	private static final String FAILJOB = "failjob";
	private static final String MATERIAL = "material";
	private static final String DY = "dy";
	private static final String DX = "dx";
	private static final String DIRECTION = "direction";
	private static final String TYPE = "type";
	private static final String ATTR_TIME = "time";
	private static final String SEARCH = "search";
	private static final String NAME = "name";

	private final EBuildingJobType type;
	private short dx;
	private short dy;
	private EMaterialType material;
	private ESearchType searchType;
	private String successjob;
	private String failjob;
	private float time;
	private EDirection direction;
	private String name;

	JobElementWrapper(Attributes attributes) {
		type = getType(attributes);
		dx = (short) getAttributeAsInt(attributes, DX);
		dy = (short) getAttributeAsInt(attributes, DY);
		material = getMaterial(attributes);
		searchType = getSearchType(attributes);
		name = attributes.getValue(NAME);
		successjob = attributes.getValue(SUCCESSJOB);
		failjob = attributes.getValue(FAILJOB);
		time = getAttributeAsFloat(attributes, ATTR_TIME);
		direction = getDirection(attributes);
	}

	private static EBuildingJobType getType(Attributes attributes)
			throws IllegalAccessError {
		String typeString = attributes.getValue(TYPE);
		try {
			return EBuildingJobType.valueOf(typeString);
		} catch (IllegalArgumentException e) {
			throw new IllegalAccessError("Job has unknown type: " + typeString);
		}
	}

	@Override
	public EDirection getDirection() {
		return direction;
	}

	private static EDirection getDirection(Attributes attributes) {
		String string = attributes.getValue(DIRECTION);
		if (string == null) {
			return null;
		} else {
			try {
				return EDirection.valueOf(string);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
	}

	private static int getAttributeAsInt(Attributes attributes, String attribute) {
		String string = attributes.getValue(attribute);
		if (string == null) {
			return 0;
		} else {
			try {
				return Integer.parseInt(string);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	@Override
	public short getDx() {
		return dx;
	}

	@Override
	public short getDy() {
		return dy;
	}

	private static EMaterialType getMaterial(Attributes attributes) {
		String string = attributes.getValue(MATERIAL);
		if (string == null) {
			return null;
		} else {
			try {
				return EMaterialType.valueOf(string);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
	}

	@Override
	public EMaterialType getMaterial() {
		return material;
	}

	public ESearchType getSearchType(Attributes attributes) {
		String string = attributes.getValue(SEARCH);
		if (string == null) {
			return null;
		} else {
			try {
				return ESearchType.valueOf(string);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
	}

	@Override
	public ESearchType getSearchType() {
		return searchType;
	}

	@Override
	public String getNextFailJob() {
		return failjob;
	}

	@Override
	public String getNextSucessJob() {
		return successjob;
	}

	@Override
	public float getTime() {
		return time;
	}

	private static float getAttributeAsFloat(Attributes attributes, String attribute) {
		String string = attributes.getValue(attribute);
		if (string == null) {
			return 0f;
		} else {
			try {
				return Float.parseFloat(string);
			} catch (NumberFormatException e) {
				return 0f;
			}
		}
	}

	@Override
	public EBuildingJobType getType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}

}
