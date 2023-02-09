package LikelionProject.Controller;

import LikelionProject.Domain.Announcement;
import LikelionProject.Domain.MyFile;
import LikelionProject.Repository.AnnounceRepository;
import LikelionProject.Repository.MyFileRepository;
import LikelionProject.Service.AnnounceService;
import LikelionProject.Service.MyFileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AnnounceController {

    final AnnounceRepository announceRepository;
    final AnnounceService announceService;
    final MyFileRepository myFileRepository;
    final MyFileService myFileService;

    @GetMapping("/")
    public String Main() {
        return "main";
    }

    @GetMapping("/list/view")
    public String View(Long id, Model model) {
        model.addAttribute("Announcement", announceService.getView(id));
        return "view";
    }

    @GetMapping("/list")
    public String writeList(@RequestParam(value = "page", defaultValue = "0") int page, Model model){

        Page<Announcement> list = announceService.getAnnounceList(page);

        int nowPage = list.getPageable().getPageNumber() + 1;
        double paging = nowPage / (double) 10;
        int endPage = (int) (Math.ceil(paging) * 10);
        int tempEndPage = (int) (Math.ceil(list.getTotalPages() / (double) 10) * 10);

        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }
        int startPage = (endPage - 10) + 1;
        if(startPage <= 0) startPage = 1;

        if(endPage > list.getTotalPages()) {
            endPage = list.getTotalPages();
        }

        if(startPage == list.getTotalPages()) {
            endPage = startPage;
        }

        model.addAttribute("postList", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prev", list.getPageable().previousOrFirst().getPageNumber());
        model.addAttribute("next", list.getPageable().next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        return "list";
    }

    @GetMapping("/write")
    public String Write() {
        return "write";
    }

    @PostMapping("/postWrite")
    public String postWrite(Announcement announcement, MultipartFile file) throws Exception{
        if(!file.isEmpty()) {
            String projectpath = System.getProperty("user.dir") + "/src/main/resources/static/files";
            UUID uuid = UUID.randomUUID();
            String filename = uuid+"_"+file.getOriginalFilename();
            File saveFile = new File(projectpath, filename);
            file.transferTo(saveFile);
            announcement.setFileName(filename);
            announcement.setFilePath("/files/"+filename);
        }

        announceRepository.save(announcement);

        return "redirect:/";
    }

    @GetMapping("/list/view/delete")
    public String ViewDelete(@RequestParam("id") Long id) {
        announceRepository.deleteById(id);
        return "redirect:/list";
    }

    @GetMapping("/pictureList")
    public String pictureList(@RequestParam(value = "page", defaultValue = "0") int page, Model model){

        Page<MyFile> myFileList = myFileService.getPictureList(page);

        int nowPage = myFileList.getPageable().getPageNumber() + 1;
        double paging = nowPage / (double) 10;
        int endPage = (int) (Math.ceil(paging) * 10);
        int tempEndPage = (int) (Math.ceil(myFileList.getTotalPages() / (double) 10) * 10);

        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }
        int startPage = (endPage - 10) + 1;
        if(startPage <= 0) startPage = 1;

        if(endPage > myFileList.getTotalPages()) {
            endPage = myFileList.getTotalPages();
        }

        if(startPage == myFileList.getTotalPages()) {
            endPage = startPage;
        }

        model.addAttribute("pictureList", myFileList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prev", myFileList.getPageable().previousOrFirst().getPageNumber());
        model.addAttribute("next", myFileList.getPageable().next().getPageNumber());
        model.addAttribute("hasNext", myFileList.hasNext());
        model.addAttribute("hasPrev", myFileList.hasPrevious());

        return "pictureList";
    }

    @GetMapping("/picture")
    public String picture() {
        return "pictureWrite";
    }

    @PostMapping("/pictureWrite")
    public String pictureWrite(MyFile myFile, MultipartFile image) throws Exception{
        if(!image.isEmpty()) {
            String projectpath = System.getProperty("user.dir") + "/src/main/resources/static/images";
            UUID uuid = UUID.randomUUID();
            String filename = uuid+"_"+image.getOriginalFilename();
            File saveFile = new File(projectpath, filename);
            image.transferTo(saveFile);
            myFile.setMyFileName(filename);
            myFile.setMyFilePath("/images/"+filename);
        }

        myFileRepository.save(myFile);

        return "redirect:/";
    }
}
