package tour.nonghaeng.global.infra.service;

public interface LikesService<Entity> {

    void plusLikes(Entity entity);

    void minusLikes(Entity entity);
}
