package com.bridgelabz.fundoo.note.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.exceptions.GetURLInfoException;
import com.bridgelabz.fundoo.note.models.URLMetaData;

@Service
public class ContentScrappingJsoupImpl implements ContentScrappingService {


	private URLMetaData getLinkInformation(String link) throws GetURLInfoException {
		Document doc = null;
		String title = null;
		String imageUrl = null;
		try {
			
			doc = Jsoup.connect(link).get();
			title = doc.title();
			Element image = doc.select("img").first();
			if (image != null)
				imageUrl = image.absUrl("src");
		} catch (IOException exception) {
			throw new GetURLInfoException("unable to fetch link information");
		}

		URLMetaData urlMetaData = new URLMetaData();
		urlMetaData.setUrl(link);
		urlMetaData.setImageUrl(imageUrl);
		urlMetaData.setUrlTitle(title);

		return urlMetaData;
	}

	public List<URLMetaData> analyseDescription(String description) throws GetURLInfoException {

		String regex = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
		String[] desciptionArray = description.split("\\s+");

		List<URLMetaData> listOfURLMetaData = new ArrayList<>();
		for (String eachEle : desciptionArray) {
			if (eachEle.matches(regex)) {
				listOfURLMetaData.add(getLinkInformation(eachEle));
			}
		}
		return listOfURLMetaData;
	}
}
