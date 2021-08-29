package com.faforever.client.update;

import lombok.Data;

import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.List;

@Data
// TODO since this class contains both, update info and configuration, the package 'update' doesn't really fit.
/**
 * A representation of a config file read from the faf server on start up. The file on the server allows to dynamically change settings in the client remotely.
 */
public class ClientConfiguration {
  private ReleaseInfo latestRelease;
  private List<ServerEndpoints> endpoints;
  private GitHubRepo gitHubRepo;
  private OAuth oAuth;

  @Data
  public static class GitHubRepo {
    /**
     * Api URL to the client GitHub Repo
     */
    private String apiUrl;
  }

  @Data
  public static class ServerEndpoints {
    private String name;
    private SocketEndpoint lobby;
    private SocketEndpoint irc;
    private SocketEndpoint liveReplay;
    private UrlEndpoint api;
    private UrlEndpoint oauth;
  }

  @Data
  public static class SocketEndpoint {
    private String host;
    private int port;
  }

  @Data
  public static class UrlEndpoint {
    private String url;
  }

  @Data
  public static class ReleaseInfo {
    private String version;
    private String minimumVersion;
    private URL windowsUrl;
    private URL linuxUrl;
    private URL macUrl;
    private boolean mandatory;
    private String message;
    private URL releaseNotesUrl;
  }

  @Data
  public static class OAuth {
    /**
     * A list of allowed redirect URIs when opening a local HTTP server. The host should always be {@literal localhost}.
     * The client would preferably open a random port, however, the server will check the redirect URI and does not
     * allow random ports. Therefore, the server needs to tell the client which URIs it might use. One would be enough
     * but since the port might not be available, multiple are needed.
     */
    private Collection<URI> redirectUris;
  }
}