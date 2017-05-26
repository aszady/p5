# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.
use Mix.Config

# General application configuration
config :rest_endpoint,
  ecto_repos: [RestEndpoint.Repo],
  calculator: "http://calculator:8083",
  logger: "http://logger:8082",
  cache: "http://cache:8081"

# Configures the endpoint
config :rest_endpoint, RestEndpoint.Endpoint,
  url: [host: "localhost"],
  secret_key_base: "rvYau1XFb2n9lmb36nz0sbRBS/QIZb4uNrq0khteOJ9XgYz03lxRQEImO49gQOXE",
  render_errors: [view: RestEndpoint.ErrorView, accepts: ~w(html json)],
  pubsub: [name: RestEndpoint.PubSub,
           adapter: Phoenix.PubSub.PG2]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env}.exs"
