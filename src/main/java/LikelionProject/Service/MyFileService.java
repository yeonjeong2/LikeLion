package LikelionProject.Service;

import LikelionProject.Domain.Announcement;
import LikelionProject.Domain.MyFile;
import LikelionProject.Repository.MyFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyFileService {

    @Autowired
    private MyFileRepository myFileRepository;

    public Page<MyFile> getPictureList(int page) {
        return myFileRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
    }
}
