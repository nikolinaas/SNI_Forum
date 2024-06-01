package org.unibl.etf.ip.sni_projekat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.sni_projekat.model.entities.CommentEntity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Theme {


    private Integer id;

    private String themeName;

    private List<CommentEntity> commentsById;

}
