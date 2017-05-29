defmodule RestEndpoint.Router do
  use RestEndpoint.Web, :router

  pipeline :browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_flash
    plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", RestEndpoint do
    pipe_through :browser # Use the default browser stack

    get "/", PageController, :index
  end

  scope "/api", RestEndpoint do
    pipe_through :api

    get "/weather", WeatherController, :get_weather
  end
end
