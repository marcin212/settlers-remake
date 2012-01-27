package jsettlers.main;

import java.util.List;

import jsettlers.common.network.IMatch;
import jsettlers.common.network.IMatchSettings;
import jsettlers.graphics.startscreen.INetworkConnector;
import jsettlers.graphics.startscreen.IStartScreenConnector;
import jsettlers.logic.map.save.MapList;
import jsettlers.main.network.NetworkMatchRetriever;

class StartConnector implements IStartScreenConnector {
	/**
     * 
     */
	private final IGameStarter gamestarter;
	private final MapList mapList;
	private INetworkConnector networkConnector;

	/**
	 * @param managedJSettlers
	 */
	StartConnector(IGameStarter managedJSettlers) {
		gamestarter = managedJSettlers;
		mapList = MapList.getDefaultList();
	}

	@Override
	public List<? extends IMapItem> getMaps() {
		return mapList.getFreshMaps();
	}

	@Override
	public List<? extends ILoadableGame> getLoadableGames() {
		return mapList.getSavedMaps();
	}

	@Override
	public void startNewGame(IGameSettings game) {
		gamestarter.startGame(game);
	}

	@Override
	public void loadGame(ILoadableGame load) {
		gamestarter.loadGame(load);
	}

	@Override
	public void exitGame() {
		System.exit(0);
	}

	@Override
	public INetworkConnector getNetworkConnector() {
		if (networkConnector == null) {
			networkConnector = new NetworkMatchRetriever();
		}

		return networkConnector;
	}

	@Override
	public void startNetworkGame(IMatchSettings gameSettings) {
		gamestarter.startNetworkGame(getNetworkConnector().getServerAddress(), gameSettings);
	}

	@Override
    public void joinNetworkGame(IMatch match) {
		if (networkConnector != null) {
			gamestarter.joinNetworkGame(networkConnector.getServerAddress(), match);
		}
    }
}