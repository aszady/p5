defmodule RestEndpoint.PageController do
  use RestEndpoint.Web, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end
end
