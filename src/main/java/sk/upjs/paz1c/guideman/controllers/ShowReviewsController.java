package sk.upjs.paz1c.guideman.controllers;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sk.upjs.paz1c.guideman.storage.DaoFactory;
import sk.upjs.paz1c.guideman.storage.EventDao;

public class ShowReviewsController {

	private EventDao eventDao = DaoFactory.INSTANCE.getEventDao();

	@FXML
	private Label reviewsLabel;

	@FXML
	private ListView<String> reviewsListView;

	@FXML
	void initialize() {

		Long idEvent = ShowTour.INSTANCE.getLoggedEvent().getId();
		List<String> listReviews = eventDao.getReviews(idEvent);

		reviewsListView.setItems(FXCollections.observableArrayList(listReviews));
		reviewsListView.setMouseTransparent(true);
		reviewsListView.getSelectionModel().clearSelection();

	}

}
