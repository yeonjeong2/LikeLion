package LikelionProject.Repository;

import LikelionProject.Domain.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyFileRepository extends JpaRepository<MyFile, Long> {

}
