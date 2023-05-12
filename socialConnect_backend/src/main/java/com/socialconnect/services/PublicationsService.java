package com.socialconnect.services;

import java.util.List;

import com.socialconnect.model.Publication;
import com.socialconnect.model.User;

public interface PublicationsService {

	public Publication savePublication(Publication publication) throws Exception;
	public Publication getPublication(Long id);
	public List<Publication> getPublications(User user);
	public void deletePublication(long id);
}
